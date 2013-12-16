package de.hska.wifl1011.ws1314.projektarbeit

import scala.actors.Actor
import scala.actors.Actor._

case class AddAuction(auction: Auction)
case class AuctionListWrapper(auctions: List[Auction])
case class GetAuctionsList()

class Auctionhouse(numberOfAuctions: Int) extends Actor {

  var auctions: List[Auction] = List()
  var auctionsLeft: Int = numberOfAuctions 

  def act() {
    loop {
      react {
  
        case AddAuction(auction) => {
          auctionsLeft = auctionsLeft - 1
          auctions = auctions ::: List(auction)
        }
        
        case GetAuctionsList() => sender ! AuctionListWrapper(auctions)
		
        case _ => println("[" + toString + "]: Unknown Message!")

      }
    }
  }

  override def toString(): String = {
    "Auctionhouse"
  }    
}
