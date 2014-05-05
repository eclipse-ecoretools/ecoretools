#!/bin/env/python
import os
import zipfile
from urllib2 import urlopen, URLError, HTTPError


def unzip(path,target):
    if not target.endswith(':') and not os.path.exists(target):
      os.mkdir(target)
    zfile = zipfile.ZipFile(path)
    for name in zfile.namelist():
        (dirname, filename) = os.path.split(name)
        dirname = os.path.join(target,dirname)
        if filename == '':
            # directory
            if not os.path.exists(dirname):
                os.mkdir(dirname)
        else:
            # file
            fd = open(os.path.join(target,name), 'w')
            fd.write(zfile.read(name))
            fd.close()
    zfile.close()
    
def dlfile(url,target):
    # Open the url
    try:
        f = urlopen(url)
        print "downloading " + url

        # Open our local file for writing
        with open(target, "wb") as local_file:
            local_file.write(f.read())

    #handle errors
    except HTTPError, e:
        print "HTTP Error:", e.code, url
    except URLError, e:
        print "URL Error:", e.reason, url


def main():
  
  docID = "1kbM2KtQIs2GooR9Wqf05HhwW4y0ByJIColtfA72lPdE"
  remoteFileUrl="https://docs.google.com/feeds/download/documents/export/Export?id=%s&format=zip" % docID
  targetFolder="../org.eclipse.emf.ecoretools.design/doc/"
  zipFile=targetFolder + 'dl.zip'
  
  dlfile(remoteFileUrl,zipFile) 
  unzip(zipFile,targetFolder)
  os.remove(zipFile)
  

if __name__ == '__main__':
    main()    
