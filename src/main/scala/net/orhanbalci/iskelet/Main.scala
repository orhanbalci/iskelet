package net.orhanbalci.iskelet
import org.rogach.scallop._
import net.jcazevedo.moultingyaml._
import net.jcazevedo.moultingyaml.DefaultYamlProtocol._
import better.files.{File => ScalaFile, _}
import better.files.Dsl._

class Conf(settings: Seq[String]) extends ScallopConf(settings) {
  version("iskelet 0.1.0 (c) 2017 Orhan Balci")
  banner("""Usage: java -jar iskelet.jar [OPTION]
           |Iskelet creates folder structure described in configfile option
           |Options:
           |""".stripMargin)
  val root =
    opt[String](descr = "Root directory where folder structure will be created")
  val configfile = opt[String](descr = "Folder structure definition yaml file")
  verify()
}

object Main {

  def main(args: Array[String]): Unit = {
    val conf = new Conf(args);
    val configFileContent = ScalaFile(conf.configfile()).contentAsString;
    val configAst         = configFileContent.parseYaml;
    createFolder(conf.root().toFile, configAst.asInstanceOf[YamlObject]);
  }

  def createFolder(parent: ScalaFile, folders: YamlObject) {
    folders.fields foreach {
      case (YamlString(key), value) => {

        if (key.contains(".")) {
          println(s"Creating file $parent/$key")
          (parent / key).createIfNotExists();
        } else {
          println(s"Creating folder $parent/$key")
          mkdir(parent / key)
        }

        if (value.isInstanceOf[YamlObject]) {
          createFolder(parent / key, value.asInstanceOf[YamlObject]);
        }
      }
    }
  }
}
