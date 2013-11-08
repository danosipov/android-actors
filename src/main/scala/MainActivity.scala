package com.danosipov.androidactors

import android.app.Activity
import android.view.View
import android.os.Bundle
import android.widget.Toast
import akka.actor._
import akka.actor.LightArrayRevolverScheduler

class MainActivity extends Activity with TypedActivity {

  override def onCreate(bundle: Bundle) {
    super.onCreate(bundle)

    // Create the 'helloakka' actor system
    val system = ActorSystem("helloakka")

    // Create the 'greeter' actor
    val buttonActor = system.actorOf(Props[ButtonActor], "greeter")

    // Create an "actor-in-a-box"
    val inbox = Inbox.create(system)

    setContentView(R.layout.main)
    //findView(TR.textview).setText("hello, world!")
    findView(TR.button1).setOnClickListener((v: View) => {
      buttonActor.tell(v.getTag, ActorRef.noSender)
    })
    findView(TR.button2).setOnClickListener((v: View) => {
      buttonActor.tell(v.getTag, ActorRef.noSender)
    })
    findView(TR.button3).setOnClickListener((v: View) => {
      Toast.makeText(this, "Button 3 clicked", Toast.LENGTH_LONG).show()
    })
  }

}
class ButtonActor extends Actor {
  def receive = {
    case "btn1" => println("Button 1 clicked")
    case "btn2" => println("Button 2 clicked")
    case "btn3" => println("Button 3 clicked")
    case _ => println("Say What?")
  }
}
