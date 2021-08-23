package graphics

import doodle.core.Transform.translate
import doodle.core._
import doodle.image._
import doodle.image.syntax._
import doodle.image.syntax.core._
import doodle.java2d._
import doodle.java2d.effect.Frame

def display(ns: List[Int])(image: Image): Unit =
  val frameTitle = s"N-Queens Problem - Solutions for N = ${ns.mkString(",")}"
  val frameWidth = 1800
  val frameHeight = 1000
  val frameBackgroundColour = Color.white
  val frame = Frame.size(frameWidth,frameHeight).title(frameTitle).background(frameBackgroundColour)
  image.draw(frame)

def makeSquareImages(n: Int): (Image,Image) =
  val emptySquareColour = n match {
    case 4 => Color.limeGreen
    case 5 => Color.lime
    case 6 => Color.springGreen
    case 7 => Color.paleGreen
    case 8 => Color.greenYellow
    case other => Color.white
  }
  val square: Image = Image.square(10).strokeColor(Color.black)
  val emptySquare: Image = square.fillColor(emptySquareColour)
  val fullSquare: Image = square.fillColor(Color.orangeRed)
  (emptySquare, fullSquare)

val paddingImage = Image.square(10).strokeColor(Color.white).fillColor(Color.white)  