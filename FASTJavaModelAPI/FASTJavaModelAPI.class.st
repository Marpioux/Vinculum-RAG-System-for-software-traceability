Class {
	#name : 'FASTJavaModelAPI',
	#superclass : 'Object',
	#classInstVars : [
		'fastModel'
	],
	#category : 'FASTJavaModelAPI',
	#package : 'FASTJavaModelAPI'
}

{ #category : 'as yet unclassified' }
FASTJavaModelAPI class >> creationModelFromString: aString [
| model |

model := JavaSmaCCProgramNodeImporterVisitor new parseCodeString: aString.

"for testing in local"
"model installWithCache: false."

^ model.
]

{ #category : 'as yet unclassified' }
FASTJavaModelAPI class >> creationOfJSONClass: aClassModel [
| arg1 |
"creation of a json from a fast class"

arg1 := { 
	('name' -> aClassModel name).
 } asDictionary.

^arg1.
]

{ #category : 'as yet unclassified' }
FASTJavaModelAPI class >> creationOfJSONMethod: methodModel [
| arg1 |

arg1 := { 
('name' -> methodModel name).
('sourceCode' -> methodModel sourceCode).
('parentClass' -> methodModel javaDeclarationOwner name).
 } asDictionary.

^ arg1.
]

{ #category : 'as yet unclassified' }
FASTJavaModelAPI class >> fastModel [

	^fastModel.
]

{ #category : 'accessing' }
FASTJavaModelAPI class >> fastModel: anObject [

	fastModel := anObject.
]

{ #category : 'as yet unclassified' }
FASTJavaModelAPI class >> getRequest: arg1 param: arg2 as: arg3 [

	^ ((arg1 isKindOf: TeaRequest)
		   ifTrue: [ arg1 at: arg2 ]
		   ifFalse: [ arg1 ]) perform: arg3

]

{ #category : 'command line' }
FASTJavaModelAPI class >> hello [ 
<REST_API: 'GET' versions: #('v1') pattern: 'hello'>

^'Hello Marius'
]

{ #category : 'as yet unclassified' }
FASTJavaModelAPI class >> retrieveClass: aRequest [
| answer |
"return all classes and some informations on them"
<REST_API: 'GET' versions: #('v1') pattern: 'class'>

answer := self fastModel allFASTJavaClassDeclaration collect: [ :class |
	self creationOfJSONClass: class ].

^ STONJSON toString: answer asArray.
]

{ #category : 'as yet unclassified' }
FASTJavaModelAPI class >> retrieveClassWIthoutAPI [
| answer |
"return all classes and some informations on them"

answer := self fastModel allFASTJavaClassDeclaration collect: [ :class |
	"self creationOfJSONClass: class" class].

^ answer.
]

{ #category : 'as yet unclassified' }
FASTJavaModelAPI class >> retrieveClassbyName: aRequest [
<REST_API: 'GET' versions: #('v1') pattern: 'class/<aClassName>'>
"SHOULD RETURN THE CLASS MATCHING THE NAME"
| aClassName |

aClassName :=  self getRequest: aRequest param: #aClassName as: #asString.

self fastModel allFASTJavaClassDeclaration do: [ :class |
	 (class name equalsTo: aClassName ) ifTrue: [^ STONJSON toString: ((self creationOfJSONClass: class)asArray) ]].
]

{ #category : 'as yet unclassified' }
FASTJavaModelAPI class >> retrieveCompleteJson: aRequest [
<REST_API: 'GET' versions: #('v1') pattern: 'getJson/<aFilePath>'>
"retrieve a complete JSON for each method and class of a Java file"
| aFilePath content model classes allInfos |

aFilePath := aRequest.
"aFilePath :=  self getRequest: aRequest param: #aFilePath as: #asString."
content := aFilePath asFileReference contents.
model := self creationModelFromString: content.
self fastModel: model. 

classes := self retrieveClassWIthoutAPI. 

allInfos := classes collect: [ :classDic | 
    | filteredMethods |
    filteredMethods := classDic declarations select: [ :method | method isKindOf: FASTJavaMethodEntity ].
    {(classDic name -> ((filteredMethods collect: [ :method | self retrieveMethod: method name ])asArray) )} asDictionary
].

^ STONJSON toString: allInfos first. 


]

{ #category : 'as yet unclassified' }
FASTJavaModelAPI class >> retrieveMethod: aRequest [
"<REST_API: 'GET' versions: #('v1') pattern: 'method/<aMethodName>'>
""retrieve a function by his name""
| aMethodName |"

"aMethodName :=  self getRequest: aRequest param: #aMethodName as: #asString."

self fastModel allFASTJavaMethodEntity do: [ :method |
	 (method name equalsTo: aRequest "aMethodName for API") ifTrue: [^ self creationOfJSONMethod: method]
	].
]

{ #category : 'as yet unclassified' }
FASTJavaModelAPI class >> retrieveMethods: aRequest [
<REST_API: 'GET' versions: #('v1') pattern: 'methods'>
| model listMethods answer |
"retrieve all methods and some informations on them"


model := self fastModel.
listMethods := model allFASTJavaMethodEntity.

answer := listMethods collect: [ :method | self creationOfJSONMethod: method ].

^STONJSON toString: answer asArray.

]

{ #category : 'as yet unclassified' }
FASTJavaModelAPI class >> retrieveMethodsWithoutAPI [
| model listMethods answer |
"retrieve all methods and some informations on them"
model := self fastModel.
listMethods := model allFASTJavaMethodEntity.

answer := listMethods collect: [ :method | self creationOfJSONMethod: method ].

^answer asArray.

]
