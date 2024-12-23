Class {
	#name : 'TealightServer',
	#superclass : 'Object',
	#category : 'FASTJavaModelAPI',
	#package : 'FASTJavaModelAPI'
}

{ #category : 'initialization' }
TealightServer >> initialize [

	| tmp2 |
	tmp2 := TLWebserver teapot.
	tmp2
		GET: '/hi' -> 'HelloWorld'
]
