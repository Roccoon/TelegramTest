import info.mukel.telegrambot4s.api._
import info.mukel.telegrambot4s.methods._
import info.mukel.telegrambot4s.models._

/**
  * Echo, ohcE
  */
class EchoBot(val token: String) extends TelegramBot
  with Polling {

  override def receiveMessage(msg: Message): Unit = {
println("1")
    for (text <- msg.text)
      request(SendMessage(msg.source, text.reverse+" че за?" ))
  }
}
