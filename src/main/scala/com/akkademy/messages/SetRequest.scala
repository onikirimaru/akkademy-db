package com.akkademy.messages

case class SetRequest(key: String, value: Object)

case class GetRequest(key: String)

case class KeyNotFoundException(key: String) extends Exception

case class SetIfNotExistsRequest(key: String, value: Object) extends Exception

case class DeleteRequest(key: String) extends Exception
