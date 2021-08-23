package queens

def queens(n: Int): List[List[Int]] =
  def placeQueens(k: Int): List[List[Int]] =
    if k == 0
    then List(List())
    else
      for
        queens <- placeQueens(k - 1)
        queen <- 1 to n
        if safe(queen, queens)
      yield queen :: queens

  placeQueens(n)

def safe(queen: Int, queens: List[Int]): Boolean =
  val (row, column) = (queens.length, queen)
  val safe: ((Int,Int)) => Boolean = (nextRow, nextColumn) =>
    column != nextColumn && ! onDiagonal(column, row, nextColumn, nextRow)
  zipWithRows(queens) forall safe

def onDiagonal(row: Int, column: Int, otherRow: Int, otherColumn: Int) =
  math.abs(row - otherRow) ==  math.abs(column - otherColumn)

def zipWithRows(queens: List[Int]): Iterable[(Int,Int)] =
  val rowCount = queens.length
  val rowNumbers = rowCount - 1 to 0 by -1
  rowNumbers zip queens