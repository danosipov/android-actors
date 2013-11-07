package com.danosipov

import android.app.Activity
import android.view.View
import android.os.Bundle
import android.widget.Toast
import akka.actor._
import akka.actor.LightArrayRevolverScheduler

class MainActivity extends Activity with TypedActivity {

  implicit def view2onClickListener(f: (View => Unit)): View.OnClickListener = {
    new View.OnClickListener() {
      override def onClick(v: View) {
        f(v)
      }
    }
  }

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
      buttonActor.tell(v, ActorRef.noSender)
    })
    findView(TR.button1).setOnClickListener((v: View) => {
      buttonActor.tell(v, ActorRef.noSender)
    })
    findView(TR.button1).setOnClickListener((v: View) => {
      Toast.makeText(this, "Button 1 clicked", Toast.LENGTH_LONG).show()
    })
  }

}
class ButtonActor extends Actor {
  def receive = {
    case TR.button1 => wait(3000); println("Button 1 clicked")
    case TR.button2 => wait(3000); println("Button 2 clicked")
    case TR.button3 => wait(3000); println("Button 3 clicked")
    case _ => println("Say What?")
  }
}
