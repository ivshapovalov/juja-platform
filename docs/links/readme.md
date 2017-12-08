# Links
This service store useful links for the Juja community 

## Technical characteristics of the project:

* JDK8
* Mongo
* SpringBoot
* SpringData
* SpringMVC
* jUnit
* JBot Framework
* Lombok

### LKS-F1
***LKS-F1 As a user I want to save new useful link***

* LKS-F1-D1 Result of the command is the Link entity

* LKS-F1-URL
```
    url - "/links"
    method - POST
```
* LKS-F1-REQ
```
{
  "url": "example.url"
}
```
* LKS-F1-RSP - Saved Link entity
```
   {
     "id": .... ,
     "url":....
   }  
```
* LKS-F1-RSP-ERR
```
    {
    error message
    }
```


### LKS-F2
***LKS-F2 As a user I want to get all links.***

* LKS-F2-D1 The operation is available for any user.
* LKS-F2-D2 Result of the command is list of Links entities.

* LKS-F2-URL
```
    url - "/links"
    method - GET
```
* LKS-F2-REQ
```
   {}
```
* LKS-F2-RSP
```
    [
        {
            "id": .... ,
            "url":....           
        },
        {
            ....
        },
        ....   
    ]
```
* LKS-F2-RSP-ERR
```
    {
    error message
    }
```
