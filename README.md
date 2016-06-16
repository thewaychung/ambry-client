# Ambry-client
Ambry-client is a Java SDK to call the Ambry, a distributed object store, RESTful API use Java code. If you don't have knowledge about Ambry, e.g.: how to install Ambry, what is ambryId. Please firstly read [Ambry Wiki](https://github.com/linkedin/ambry/wiki) and then come back to use this library.

# How do I use it?

###Maven

Use it as a maven dependency (TBD)

###How to use
```Java
AmbryClient ambryClient = AmbryClient("localhost", 1174);

// 1.healthCheck
AmbryBaseResponse response = ambryClient.healthCheck();
String status = response.getStatus();
// return: GOOD

// 2.GET-blobInfo
AmbryBlobInfoResponse response = ambryClient.getFileProperty(ambryId)
// return: blobSize, serviceId, creationTime, private, contentType, ownerId, umDesc

// 3.GET-blob
AmbryGetFileResponse response = ambryClient.getFile(ambryId);
byte[] data = ambryClient.readFromStream(response.getContent());
// return: blobSize, contentType, content(InputStream)
// OR use:
AmbryGetFileResponse response = ambryClient.getFile(ambryId, localFile);
// this method will download blob and write into the local file passed in

// 4.POST
// upload file with filepath and file type string
AmbryPostFileResponse postResponse = ambryClient.postFile(filePath, "text/plain");
// upload file with file object and type string
AmbryPostFileResponse postResponse = ambryClient.postFile(file, "image/jpg");
// upload file with byte array and type string
byte[] content = getFileBytes(file);
AmbryPostFileResponse postResponse = ambryClient.postFile(content, fileTypeString);
// return: ambryId

// 5.DELETE
AmbryBaseResponse delResponse = ambryClient.deleteFile(ambryId);
// return: code(202), status, message
```

#License
Copyright (c) 2016 Zhong Zewei

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
