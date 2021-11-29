class Board {

    //true value means that the field is occupied
    private val board: Array<BooleanArray>
    private val maxYPosition = 6

    constructor(day: Int, month: Int) {
        this.board = arrayOf(
            BooleanArray(maxYPosition),
            BooleanArray(maxYPosition),
            BooleanArray(7),
            BooleanArray(7),
            BooleanArray(7),
            BooleanArray(7),
            BooleanArray(3)
        )
        setMonth(month - 1)
        setDay(day - 1)
    }

    private constructor(board: Array<BooleanArray>) {
        this.board = board
    }

    private fun setDay(day: Int) {
        board[2 + day / 7][day % 7] = true
    }

    private fun setMonth(month: Int) {
        board[month / maxYPosition][month % maxYPosition] = true
    }

    fun isOccupied(x: Int, y: Int): Boolean = board.getOrNull(y)?.getOrNull(x) ?: true

    fun isSolved() = board.all { it.all { it1 -> it1 } }

    fun nextSteps(
        availablePieces: Map<Piece, Set<Piece>>,
        solutionSoFar: List<Pair<Piece, Point2D>>
    ): Sequence<Result> {
        val startingPoint = getStartingPoint()
        return availablePieces.values.flatten().toSet().asSequence()
            .filter { it.isValidStart(startingPoint, maxYPosition) }
            .filter { it.blockPostions.map { point -> point + startingPoint }.none { (x, y) -> isOccupied(x, y) } }
            .map {
                Result(
                    place(it, startingPoint),
                    availablePieces.filterNot { variants -> variants.value.contains(it) },
                    solutionSoFar + (it to startingPoint)
                )
            }
    }

    private fun getStartingPoint(): Point2D =
        board.asSequence().flatMapIndexed() { y, booleanArray ->
            booleanArray.asSequence().withIndex().filter { !it.value }.map { Point2D(it.index, y) }
        }.firstOrNull() ?: throw IllegalStateException("No starting point found")


    private fun place(piece: Piece, startingPoint: Point2D): Board {
        val newBoard = board.map { it.clone() }.toTypedArray()
        piece.blockPostions.map { it + startingPoint }.forEach { newBoard[it.y][it.x] = true }
        return Board(newBoard)
    }

    fun solve(): List<Pair<Piece, Point2D>> = solve(Piece.allPieces)

    fun solve(random: Boolean): List<Pair<Piece, Point2D>> =
        if (random) {
            val shuffled = LinkedHashMap(Piece.allPieces.map { (key, value): Map.Entry<Piece, Set<Piece>> ->
                key to LinkedHashSet(
                    value.asIterable().shuffled()
                )
            }.asIterable().shuffled().toMap())
            solve(shuffled)
        } else solve()

    fun solve(pieces: Map<Piece, Set<Piece>>): List<Pair<Piece, Point2D>> {
        return nextSteps(pieces, emptyList()).flatMap { (board1, availablePieces, solution) ->
            board1.nextSteps(availablePieces, solution).flatMap { (board1, availablePieces, solution) ->
                board1.nextSteps(availablePieces, solution).flatMap { (board1, availablePieces, solution) ->
                    board1.nextSteps(availablePieces, solution).flatMap { (board1, availablePieces, solution) ->
                        board1.nextSteps(availablePieces, solution).flatMap { (board1, availablePieces, solution) ->
                            board1.nextSteps(availablePieces, solution).flatMap { (board1, availablePieces, solution) ->
                                board1.nextSteps(availablePieces, solution)
                                    .flatMap { (board1, availablePieces, solution) ->
                                        board1.nextSteps(availablePieces, solution)
                                    }
                            }
                        }
                    }
                }
            }
        }.firstOrNull { it.board.isSolved() }?.let { it.solution } ?: throw IllegalStateException("No solution found")
    }

}

data class Result(
    val board: Board,
    val availablePieces: Map<Piece, Set<Piece>>,
    val solution: List<Pair<Piece, Point2D>>
)