package com.example.englishmessenger.retrofit

data class Welcome (
    val software: Software,
    val language: Language,
    val matches: List <Matches>
)

data class Software (
    val name: String,
    val version: String,
    val buildDate: String,
    val apiVersion: Int,
    val status: String,
    val Premium: Boolean
)

data class Language (
    val name: String,
    val code: String,
    val detectedLanguage: DetectedLanguage
)

data class DetectedLanguage(
    val name: String,
    val code: String
)

data class Matches(
    val message: String,
    val shortMessage: String,
    val offset: Int,
    val length: Int,
    val replacements: List <Replacements>,
    val context: Context,
    val sentence: String,
    val rule: Rule
)

data class Replacements(
    val value: String
)

data class Context(
    val text: String,
    val offset: Int,
    val length: Int
)

data class Rule(
    val id: String,
    val subId: String,
    val description: String,
    val urls: List<Urls>,
    val issueType: String,
    val category: Category
)

data class Urls(
    val value: String
)

data class Category(
    val id: String,
    val name: String
)