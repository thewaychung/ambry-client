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

