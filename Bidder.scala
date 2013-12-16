package de.hska.wifl1011.ws1314.projektarbeit

import scala.actors.Actor
import scala.actors.Actor._
import scala.actors._
import AuctionStatus._

case class ThisBid(auction: Auction)
case class AuctionResult(state: AuctionStatus)

class Bidder(bidderId: Int, auctionhouse: Auctionhouse) extends Actor {

  def bId = bidderId

    val replyList = auctionhouse !? GetAuctionsList()
    replyList match {
      case AuctionListWrapper(auctions) => {
        println("Got a list of " + auctions.size + " auctions")
        if (auctions.isEmpty) {
          println(toString() + "No Auctions to bid on!");
        } else {
          var n: Int = 2
          while (n > 0) {
            val auctionId = scala.util.Random.nextInt(auctions.size)
            val auction = auctions(auctionId)
            auction.start
            this ! ThisBid(auction)
            n -= 1
          }
        }
      }
    }	


  def act() {
    loop {
      react {
        case ThisBid(auction) => {
          println(toString() + " tries to bid on " + auction + " and will wait for a result within 10 seconds")
          auction ! Bid(this)
          
          // only place bid if auction still running
          if (auction.getState.toString.equalsIgnoreCase("Terminated")) {
            println("Auction was terminated, does not make sense to bid here")
            exit
          }
          
          loop {
            reactWithin(10000) {
              case TIMEOUT => println("[" + toString + "] " + "Timeout, no longer interested in this auction"); exit
              case AuctionResult(WINNER) => println("[" + toString + "] " + "I won this auction."); exit
            }          
          }
        }
      }
    }
  }

  override def toString(): String = {
    "Bidder: " + bId
  }   
  
}
