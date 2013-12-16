package de.hska.wifl1011.ws1314.projektarbeit

import scala.actors.Actor
import scala.actors.Actor._
import scala.actors._
import AuctionStatus._

case class Bid(bidder: Bidder)

class Auction(auctionId: Int) extends Actor {
  def a = auctionId
  
  var currentHighestBidder: Bidder = _

  def act() {
    loop {
      reactWithin(500) {

        case Bid(bidder) => {
          currentHighestBidder = bidder
          println("new bid on " + toString() + " by " + bidder)        
        }
     
        case TIMEOUT => {
          println(toString + " has timed out!")
          if (currentHighestBidder != null)
            currentHighestBidder ! AuctionResult(WINNER) 
          exit 
        }
      
        case _ => println("[" + toString + "]: Unknown Message!")

      }
    }
  }
  
  override def toString(): String = {
    "Auction Id: " + a + 
      ", Highest Bidder: " + currentHighestBidder + "."
  }   
}
