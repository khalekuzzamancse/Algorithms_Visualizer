package com.khalekuzzaman.just.cse.dsavisualizer.core.realm.realm

import io.realm.kotlin.types.RealmObject

class Person : RealmObject {
    var name: String = "Foo"
    var dog: Dog? = null
    override fun toString(): String {
        return "Person( name:$name, dog:$dog )"
    }
}

class Dog : RealmObject {
    var name: String = ""
    var age: Int = 0
    override fun toString(): String {
        return "Dog( name:$name, age:$age )"
    }
}