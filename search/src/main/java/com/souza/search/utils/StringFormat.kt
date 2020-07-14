package com.souza.search.utils

fun cropPokeUrl(url: String): String {
    return url.substringAfterLast("n/").substringBeforeLast("/")
}