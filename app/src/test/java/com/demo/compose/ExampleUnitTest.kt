package com.demo.compose

import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        runBlocking {
            launch {
                supervisorScope {
                    val j1 = Job()
                    val j2 = launch(j1) {
                        delay(1000)
                        println("hello")
                    }

                    println("j1 = $j1")
                    println("j2 = $j2")
                    delay(1000 - 50)
                    coroutineContext.cancelChildren()
                }
                println("out of scope")
            }

            delay(2000)
        }
    }

    @Test
    fun t2() {
        runBlocking {
            val request = launch {
                // it spawns two other jobs
                launch(Job()) {
                    println("job1: I run in my own Job and execute independently!")
                    delay(1000)
                    println("job1: I am not affected by cancellation of the request")
                }
                // and the other inherits the parent context
                launch {
                    delay(100)
                    println("job2: I am a child of the request coroutine")
                    delay(1000)
                    println("job2: I will not execute this line if my parent request is cancelled")
                }
            }
            delay(500)
            request.cancel() // cancel processing of the request
            println("main: Who has survived request cancellation?")
            delay(1000) // delay the main thread for a second to see what happens
        }
    }

    @Test
    fun t3() {
        runBlocking {
            val j = launch {
                launch(Job()) {
                    println("hello 1")
                    delay(1000)
                    println("hello 2")
                }
            }
            delay(1000 - 50)
            j.cancel()
            delay(1000)
        }
    }
}