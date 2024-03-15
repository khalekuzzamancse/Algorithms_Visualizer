package com.khalekuzzaman.just.cse.dsavisualizer.core.realm.realm

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

object DB {
    // use the RealmConfiguration.Builder() for more options
    val configuration = RealmConfiguration.create(schema = setOf(Person::class, Dog::class))
    val realm = Realm.open(configuration)
    fun create(){
        val person = Person().apply {
            name = "Carlo"
            dog = Dog().apply { name = "Fido"; age = 16 }
        }


        CoroutineScope(Dispatchers.Default).async {
            realm.write {
                val managedPerson = copyToRealm(person)
               println("RealmDB:create() $managedPerson")
            }
        }
    }
    fun getAll(){
        val all = realm.query<Person>().find()
       // if (all.isNotEmpty())
        println("RealmDB:getAll() ${all}")

    }
}