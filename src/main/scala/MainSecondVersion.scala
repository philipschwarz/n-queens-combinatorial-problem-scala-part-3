package mainsecondversion

import scala.language.postfixOps

import queens._
import graphics._

import doodle.image.Image

import scala.util.chaining._

type Grid[A] = List[List[A]]
type Solution = List[Int]
type Solutions = List[Solution]

@main def main =
  val ns = List(4, 5, 6, 7, 8)
  ns map queens pipe makeResultsImage pipe display(ns)

///////////////////
// Making Images //
///////////////////

val makeResultsImage = makeSolutionsImageGrid andThen combine
val makeSolutionsImage = makeBoardImageGrid andThen combine
val makeBoardImage = makeSquareImageGrid andThen combine

////////////////////////
// Making Image Grids //
////////////////////////

def makeSolutionsImageGrid(queensResults: List[Solutions]): Grid[Image] =
  makePaddedImageGrid(queensResults, makeSolutionsImage, gridWidth = 1)

def makeBoardImageGrid(solutions: List[Solution]): Grid[Image] =
  makePaddedImageGrid(solutions, makeBoardImage, gridWidth = 17)

def makeSquareImageGrid(columnIndices:Solution): Grid[Image] =
  val n = columnIndices.length
  val (emptySquare, fullSquare) = makeSquareImages(n)
  val occupiedCells: List[Boolean] =
    columnIndices.reverse flatMap { col => List.fill(n)(false).updated(col-1,true) }
  val makeSquareImage: Boolean => Image = if (_) fullSquare else emptySquare
  makeImageGrid(occupiedCells, makeSquareImage, gridWidth = n)

def makePaddedImageGrid[A](as: List[A], makeImage: A => Image, gridWidth: Int): Grid[Image] =
  makeImageGrid(as, makeImage, gridWidth) pipe insertPadding

def makeImageGrid[A](as: List[A], makeImage: A => Image, gridWidth: Int): Grid[Image] =
  as map makeImage grouped gridWidth toList

/////////////
// Padding //
/////////////

def insertPadding(images: Grid[Image]): Grid[Image] =
  import scalaz._, Scalaz._
  images map (_ intersperse paddingImage) intersperse List(paddingImage)

///////////////
// Combining //
///////////////

import cats.implicits._, cats.Monoid
val beside = Monoid.instance[Image](Image.empty, _ beside _)
val above = Monoid.instance[Image](Image.empty, _ above _)
def combine(imageGrid: List[List[Image]]): Image =
  imageGrid.foldMap(_ combineAll beside)(above)