package com.example.multiplication

interface Repository {

    interface Add {
        fun add(value: String, correct: Int, incorrect: Int)
    }

    interface ReadItem {
        fun item(id: Long): Item
    }

    interface Read {
        fun list(): List<Item>
    }

    class Base(
        private val dataSources: ItemsDao,
        private val now: Now
    ) : Add, Read, ReadItem {
        override fun add(value: String, correct: Int, incorrect: Int) {
            val id = now.nowMillis()
            dataSources.add(ItemCache(id, value, correct, incorrect))
        }

        override fun list(): List<Item> {
            return dataSources.list().map {
                Item(it.id, it.text, it.correct, it.incorrect)
            }
        }

        override fun item(id: Long): Item {
            val itemCache = dataSources.item(id)
            return Item(itemCache.id, itemCache.text, itemCache.correct, itemCache.correct)
        }


    }
}