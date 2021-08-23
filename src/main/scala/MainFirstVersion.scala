package mainfirstversion

import doodle.image.Image
import graphics._
import queens._

import scala.language.postfixOps
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

val makeResultsImage: List[Solutions] => Image = makeSolutionsImageGrid andThen combineWithPadding
val makeSolutionsImage: List[Solution] => Image = makeBoardImageGrid andThen combineWithPadding
val makeBoardImage: Solution => Image = makeSquareImageGrid andThen combine

////////////////////////
// Making Image Grids //
////////////////////////

def makeSolutionsImageGrid(queensResults: List[Solutions]): Grid[Image] =
  makeImageGrid(queensResults, makeSolutionsImage, gridWidth = 1)

def makeBoardImageGrid(solutions: List[Solution]): Grid[Image] =
  makeImageGrid(solutions, makeBoardImage, gridWidth = 17)

def makeSquareImageGrid(columnIndices:Solution): Grid[Image] =
  val n = columnIndices.length
  val (emptySquare, fullSquare) = makeSquareImages(n)
  val occupiedCells: List[Boolean] =
    columnIndices.reverse flatMap { col => List.fill(n)(false).updated(col-1,true) }
  val makeSquareImage: Boolean => Image = if (_) fullSquare else emptySquare
  makeImageGrid(occupiedCells, makeSquareImage, gridWidth = n)

def makeImageGrid[A](as: List[A], makeImage: A => Image, gridWidth: Int): Grid[Image] =
  as map makeImage grouped gridWidth toList

///////////////
// Combining //
///////////////

def combine(images: Grid[Image]): Image =
  combineWithPadding(images, paddingImage = Image.empty)

def combineWithPadding(images: Grid[Image]): Image =
  combineWithPadding(images, paddingImage)

import cats.Monoid
import cats.implicits._
val beside = Monoid.instance[Image](Image.empty, _ beside _)
val above = Monoid.instance[Image](Image.empty, _ above _)
def combineWithPadding(images: Grid[Image], paddingImage: Image): Image =
  images.map(row => row.intercalate(paddingImage)(beside))
    .intercalate(paddingImage)(above)