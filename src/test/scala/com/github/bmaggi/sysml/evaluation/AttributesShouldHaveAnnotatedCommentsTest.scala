package com.github.bmaggi.sysml.evaluation

/**
  * Created by Benoit Maggi on 11/05/2016.
  */


import org.junit.{Assert, Test}

import scala.collection.mutable.ListBuffer
import scala.xml.XML


class AttributesShouldHaveAnnotatedCommentsTest {


  @Test def testMissingAnnotatedComments() {
    val xml = XML.load(getClass.getResource("/SysML.xmi"))
    val ownedAttributeList = (xml \\ XMIUtils.ownedAttribute);
    var ownedAttributeIds = new ListBuffer[String]()
    for (ownedAttribute <- ownedAttributeList) {
      ownedAttributeIds += ownedAttribute.attribute(XMIUtils.XMI_XMNLS, "id").get.toString
    }
    val annotatedElementList = (xml \\ XMIUtils.annotatedElement);
    // remove annotated elements
    for (annotatedElement <- annotatedElementList) {
      val id: String = annotatedElement.attribute(XMIUtils.XMI_XMNLS, "idref").get.toString
      ownedAttributeIds.-=(id)
    }
    // remove base elements (we don't explicitly require a description for the tooling)
    val ownedAttributeIdsNoBase = ownedAttributeIds.filterNot(_.contains("base_"))
    Assert.assertTrue("Some elements are missing annotated comments:\n" + prettyPrint(ownedAttributeIdsNoBase), ownedAttributeIdsNoBase.isEmpty)
  }

  //TODO : there is probably a better Scala way to do that
  def prettyPrint(ownedAttributeIdsNoBase: ListBuffer[String]): String = {
    var res = new String()
    for (notAnnotated: String <- ownedAttributeIdsNoBase) {
      res ++= notAnnotated
      res ++= "\n"
    }
    return res
  }

}


