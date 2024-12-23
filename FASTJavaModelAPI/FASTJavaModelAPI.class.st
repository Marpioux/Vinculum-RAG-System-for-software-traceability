Class {
	#name : 'FASTJavaModelAPI',
	#superclass : 'Object',
	#category : 'FASTJavaModelAPI',
	#package : 'FASTJavaModelAPI'
}

{ #category : 'as yet unclassified' }
FASTJavaModelAPI class >> creationModelFromString: aString [
| model |

model := JavaSmaCCProgramNodeImporterVisitor new parseCodeString: aString.

model installWithCache: false.

^ model.
]

{ #category : 'as yet unclassified' }
FASTJavaModelAPI class >> creationOfJSONClass: aClassModel [
| arg1 |
"creation of a json from a fast class"

arg1 := { 
	('name' -> aClassModel name).
 } asDictionary.

^{((aClassModel name) -> arg1)} asDictionary
]

{ #category : 'as yet unclassified' }
FASTJavaModelAPI class >> creationOfJSONMethod: methodModel [
| arg1 |

arg1 := { 
('name' -> methodModel name).
('sourceCode' -> methodModel sourceCode).
('hasComment' -> methodModel hasComments).
('parentClass' -> methodModel javaDeclarationOwner name).
 } asDictionary.

^ { ((methodModel name) -> arg1)} asDictionary.
]

{ #category : 'as yet unclassified' }
FASTJavaModelAPI class >> fastModel [

	^MooseModel root at: 1.
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
	self creationOfJSONClass: class ].

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
FASTJavaModelAPI class >> retrieveMethod: aRequest [
<REST_API: 'GET' versions: #('v1') pattern: 'method/<aMethodName>'>
"retrieve a function by his name"
| aMethodName |

aMethodName :=  self getRequest: aRequest param: #aMethodName as: #asString.

self fastModel allFASTJavaMethodEntity do: [ :method |
	 (method name equalsTo: aMethodName) ifTrue: [^ STONJSON toString: ((self creationOfJSONMethod: method) asArray)]].
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
