package net.orhanbalci.iskelet
import org.rogach.scallop._
import net.jcazevedo.moultingyaml._
import net.jcazevedo.moultingyaml.DefaultYamlProtocol._
import better.files.{File => ScalaFile, _}
import better.files.Dsl._

class Conf(settings: Seq[String]) extends ScallopConf(settings) {
  val root       = opt[String](required = true)
  val configfile = opt[String](required = true)
  verify()
}

object Hello extends App {
  val conf = new Conf(args);
  val configFileContent = ScalaFile(conf.configfile()).contentAsString;
  val configAst         = configFileContent.parseYaml;
  createFolder(conf.root().toFile, configAst.asInstanceOf[YamlObject]);

  def createFolder(parent: ScalaFile, folders: YamlObject) {
    folders.fields foreach {
      case (YamlString(key), value) => {

        if (key.contains(".")) {
          println(s"Creating file $parent/$key")
          (parent/key).createIfNotExists();
        } 
        else{
            println(s"Creating folder $parent/$key")
            mkdir(parent/key)
        }

        if (value.isInstanceOf[YamlObject]) {
          createFolder(parent/key, value.asInstanceOf[YamlObject]);
        }
      }
    }
  }
}
