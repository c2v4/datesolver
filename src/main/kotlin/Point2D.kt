data class Point2D(val x: Int, val y: Int) {
    operator fun plus(offset: Point2D) = Point2D(x + offset.x, y + offset.y)
}
