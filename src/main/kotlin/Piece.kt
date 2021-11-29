import kotlin.math.absoluteValue

class Piece {

    // Positions of the blocks creating the piece relative to the piece's anchor point (0, 0)
    val blockPostions: Set<Point2D>
    val leftBound: Int
    val bottomBound: Int

    private constructor(blockPositions: Set<Point2D>) {
        this.blockPostions = blockPositions
        leftBound = blockPositions.minByOrNull { it.x }!!.x
        bottomBound = blockPositions.maxByOrNull { it.y }!!.y
    }

    private constructor(blocks: Array<BooleanArray>) : this(blocks.let { blocks1 ->
        val anchor = findAnchor(blocks1)
        val blockPositions = mutableSetOf<Point2D>()
        for (i in blocks1.indices) {
            for (j in 0 until blocks1[i].size) {
                if (blocks1[i][j]) {
                    blockPositions.add(Point2D(j - anchor.x, i - anchor.y))
                }
            }
        }
        blockPositions
    })


    companion object {

        val basePieces = listOf(
            Piece(
                arrayOf(
                    booleanArrayOf(true, true, true),
                    booleanArrayOf(false, false, true, true)
                )
            ),
            Piece(
                arrayOf(
                    booleanArrayOf(true, true),
                    booleanArrayOf(false, true),
                    booleanArrayOf(false, true, true)
                )
            ),
            Piece(
                arrayOf(
                    booleanArrayOf(true, true),
                    booleanArrayOf(false, true),
                    booleanArrayOf(true, true)
                )
            ),
            Piece(
                arrayOf(
                    booleanArrayOf(true, true),
                    booleanArrayOf(false, true),
                    booleanArrayOf(false, true),
                    booleanArrayOf(false, true)
                )
            ),
            Piece(
                arrayOf(
                    booleanArrayOf(false, true),
                    booleanArrayOf(true, true),
                    booleanArrayOf(false, true),
                    booleanArrayOf(false, true)
                )
            ),
            Piece(
                arrayOf(
                    booleanArrayOf(false, true),
                    booleanArrayOf(true, true),
                    booleanArrayOf(true, true)
                )
            ),
            Piece(
                arrayOf(
                    booleanArrayOf(true, true),
                    booleanArrayOf(true, true),
                    booleanArrayOf(true, true)
                )
            ),
            Piece(
                arrayOf(
                    booleanArrayOf(true, true, true),
                    booleanArrayOf(false, false, true),
                    booleanArrayOf(false, false, true)
                )
            ),
        )

        val allPieces: Map<Piece, Set<Piece>> = basePieces.asSequence().map { piece ->
            val transposed = piece.transpose()
            val xyMirrored = piece.mirrorVertical().mirrorHorizontal()
            piece to setOf(
                piece,
                transposed,
                piece.mirrorVertical(),
                piece.mirrorHorizontal(),
                transposed.mirrorVertical(),
                transposed.mirrorHorizontal(),
                xyMirrored,
                xyMirrored.transpose(),

                )
        }.toMap()

        /*
        * Finds position of the most top-left block that is true
        */
        private fun findAnchor(blocks: Array<BooleanArray>): Point2D {
            for (i in blocks.indices) {
                for (j in blocks[i].indices) {
                    if (blocks[i][j]) {
                        return Point2D(j, i)
                    }
                }
            }
            return Point2D(0, 0)
        }
    }

    private fun mirrorVertical(): Piece {
        val sizeY = bottomBound + 1
        val sizeX = leftBound.absoluteValue + blockPostions.maxByOrNull { it.x }!!.x + 1
        val newBlocks =
            Array(sizeY)
            { BooleanArray(sizeX) }
        blockPostions.forEach { (x, y) ->
            newBlocks[sizeY - y - 1][x + leftBound.absoluteValue] = true
        }
        return Piece(newBlocks)
    }

    private fun mirrorHorizontal(): Piece {
        val sizeY = bottomBound + 1
        val sizeX = leftBound.absoluteValue + blockPostions.maxByOrNull { it.x }!!.x + 1
        val newBlocks =
            Array(sizeY)
            { BooleanArray(sizeX) }
        blockPostions.forEach { (x, y) ->
            newBlocks[y][sizeX - (x + leftBound.absoluteValue) - 1] = true
        }
        return Piece(newBlocks)
    }

    private fun transpose(): Piece {
        val newBlocks =
            Array(leftBound.absoluteValue + blockPostions.maxByOrNull { it.x }!!.x + 1)
            { BooleanArray(bottomBound + 1) }
        for (block in blockPostions) {
            newBlocks[leftBound.absoluteValue + block.x][block.y] = true
        }
        return Piece(newBlocks)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Piece

        if (blockPostions != other.blockPostions) return false

        return true
    }

    override fun hashCode(): Int {
        return blockPostions.hashCode()
    }

    override fun toString(): String {
        return "Piece(blockPostions=${
            blockPostions.joinToString(
                separator = "),(",
                prefix = "(",
                postfix = ")"
            ) { "${it.x},${it.y}" }
        }, leftBound=$leftBound, bottomBound=$bottomBound)"
    }

    fun isValidStart(startingPoint: Point2D, maxY: Int) =
        leftBound + startingPoint.x >= 0 && startingPoint.y + bottomBound <= maxY


}