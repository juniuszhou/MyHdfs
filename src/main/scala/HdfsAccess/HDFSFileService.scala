package HdfsAccess

import java.io._
import java.net.URL
import org.apache.hadoop.conf._
import org.apache.hadoop.fs.FileSystem
import org.apache.hadoop.fs._
import org.apache.hadoop.io.IOUtils

/*
  if you include the hadoop common in project, you don't need the core anymore.
 */
object HDFSFileService {
  private val conf = new Configuration()
  private val hdfsCoreSitePath = new Path("/home/junius/tools/hadoop-2.7.1/etc/hadoop/core-site.xml")
  private val hdfsHDFSSitePath = new Path("/home/junius/tools/hadoop-2.7.1/etc/hadoop/hdfs-site.xml")

  conf.addResource(hdfsCoreSitePath)
  conf.addResource(hdfsHDFSSitePath)

  private val fileSystem = FileSystem.get(conf)

  def saveFile(filepath: String): Unit = {
    // val file = new File(filepath)
    val out = fileSystem.create(new Path(filepath))

    out.writeChars("Hello world\n")
    out.close()
  }

  def removeFile(filename: String): Boolean = {
    val path = new Path(filename)
    fileSystem.delete(path, true)
  }

  def getFile(filename: String) = {
    val path = new Path(filename)
    val stream = fileSystem.open(path)
    val lines = scala.io.Source.fromInputStream(stream).getLines()
    lines.foreach(println)
  }

  def getFile2(url: String) = {
    val in = new URL(url).openStream()
    IOUtils.copyBytes(in, System.out, 4096, false)

  }

  def createFolder(folderPath: String): Unit = {
    val path = new Path(folderPath)
    if (!fileSystem.exists(path)) {
      //fileSystem.mkdirs(path)
      fileSystem.createNewFile(path)
    }
  }

  def main (args: Array[String]) {
    getFile("/junData/text.txt")
    getFile2("hdfs://localhost/junData/text.txt")
  }
}

