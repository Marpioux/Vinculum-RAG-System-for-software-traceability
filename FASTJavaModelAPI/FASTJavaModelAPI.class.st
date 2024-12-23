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
 }.

^{((aClassModel name) -> arg1)}
]

{ #category : 'as yet unclassified' }
FASTJavaModelAPI class >> creationOfJSONMethod: methodModel [
| arg1 |

arg1 := { 
('name' -> methodModel name).
('sourceCode' -> methodModel sourceCode).
('hasComment' -> methodModel hasComments).
(methodModel hasComments) ifTrue: [ 
	('comment'-> methodModel comments). ].
('parentClass' -> methodModel javaDeclarationOwner name).
 }.

^ { ((methodModel name) -> arg1)}.
]

{ #category : 'as yet unclassified' }
FASTJavaModelAPI class >> fastModel [

	^MooseModel root at: 1.
]

{ #category : 'as yet unclassified' }
FASTJavaModelAPI class >> retrieveClass [
| answer |
"return all classes and some informations on them"

answer := self fastModel allFASTJavaClassDeclaration collect: [ :class |
	self creationOfJSONClass: class ].

^ answer.
]

{ #category : 'as yet unclassified' }
FASTJavaModelAPI class >> retrieveClass: aClassName [

"SHOULD RETURN THE CLASS MATCHING THE NAME"

self fastModel allFASTJavaClassDeclaration do: [ :class |
	 (class name equalsTo: aClassName ) ifTrue: [^ self creationOfJSONClass: class ]].
]

{ #category : 'as yet unclassified' }
FASTJavaModelAPI class >> retrieveMethod: aMethodName [
"retrieve a function by his name"

self fastModel allFASTJavaMethodEntity do: [ :method |
	 (method name equalsTo: aMethodName) ifTrue: [^ self creationOfJSONMethod: method ]].
]

{ #category : 'as yet unclassified' }
FASTJavaModelAPI class >> retrieveMethods [
| model listMethods answer |
"retrieve all methods and some informations on them"
model := self fastModel.
listMethods := model allFASTJavaMethodEntity.

answer := listMethods collect: [ :method | self creationOfJSONMethod: method ].

^answer.

]
