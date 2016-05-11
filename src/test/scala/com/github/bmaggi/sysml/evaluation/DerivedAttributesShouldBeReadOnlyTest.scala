package com.github.bmaggi.sysml.evaluation

/**
  * Created by Benoit Maggi on 11/05/2016.
  */

import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.junit.{Assert, Test}

import scala.xml.{Node, XML}

@RunWith(classOf[JUnit4])
class DerivedAttributesShouldBeReadOnlyTest {

  @Test def test() {
    val xml = XML.load(getClass.getResource("/SysML.xmi"))
    val ownedAttributeList = (xml \\ XMIUtils.ownedAttribute).filter(x => // filter the sequence of ownedAttribute
      ((x \\ "isDerived").nonEmpty && (x \\ "isReadOnly").isEmpty))
    Assert.assertTrue("Some elements are missing read only:\n" + prettyPrint(ownedAttributeList), ownedAttributeList.isEmpty)
  }

  //TODO : there is probably a better Scala way to do that
  def prettyPrint(ownedAttributeSeq: Seq[Node]): String = {
    var res = new String()
    for (missingReadOnly: Node <- ownedAttributeSeq) {
      res ++= missingReadOnly.attribute(XMIUtils.XMI_XMNLS, "id").get.toString
      res ++= "\n"
    }
    return res
  }

}
