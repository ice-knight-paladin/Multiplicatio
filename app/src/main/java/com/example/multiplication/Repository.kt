package com.example.multiplication

interface Repository {

    interface Add {
        fun add(value: String, correct: Int, incorrect: Int): Long
    }

    interface ReadItemMulti {
        fun item(id: Long): Item
        fun item(text: String): ItemCacheMulti?
    }

    interface ReadItemDiv {
        fun item(id: Long): Item
        fun item(text: String): ItemCacheDiv?
    }

    interface Update {
        fun update(text: String, i: Boolean)
    }

    interface Read {
        fun list(): List<Item>
    }

    class BaseMulti(
        private val dataSources: ItemsDaoMulti,
        private val now: Now
    ) : Add, Read, ReadItemMulti, Update {
        override fun add(value: String, correct: Int, incorrect: Int): Long {
            val id = now.nowMillis()
            dataSources.add(ItemCacheMulti(id, value, correct, incorrect))
            return id
        }

        override fun list(): List<Item> {
            return dataSources.list().map {
                Item(it.id, it.text, it.correct, it.incorrect)
            }
        }

        override fun item(id: Long): Item {
            val itemCache = dataSources.item(id)
            return Item(itemCache.id, itemCache.text, itemCache.correct, itemCache.incorrect)
        }

        override fun item(text: String): ItemCacheMulti? {
            val itemCache = dataSources.item(text)
            if (itemCache == null) {
                return null
            }
            return itemCache
        }

        override fun update(text: String, i: Boolean) {
            var correct = dataSources.item(text)!!.correct
            var incorrect = dataSources.item(text)!!.incorrect

            if (i)
                dataSources.update(
                    ItemCacheMulti(
                        dataSources.item(text)!!.id,
                        text,
                        correct + 1,
                        incorrect
                    )
                )
            else
                dataSources.update(
                    ItemCacheMulti(
                        dataSources.item(text)!!.id,
                        text,
                        correct,
                        incorrect + 1
                    )
                )
        }

        fun clear_table() {
            dataSources.clear_table()
        }
    }

    class BaseDiv(
        private val dataSources: ItemsDaoDiv,
        private val now: Now
    ) : Add, Read, ReadItemDiv, Update {
        override fun add(value: String, correct: Int, incorrect: Int): Long {
            val id = now.nowMillis()
            dataSources.add(ItemCacheDiv(id, value, correct, incorrect))
            return id
        }

        override fun list(): List<Item> {
            return dataSources.list().map {
                Item(it.id, it.text, it.correct, it.incorrect)
            }
        }

        override fun item(id: Long): Item {
            val itemCache = dataSources.item(id)
            return Item(itemCache.id, itemCache.text, itemCache.correct, itemCache.incorrect)
        }

        override fun item(text: String): ItemCacheDiv? {
            val itemCache = dataSources.item(text)
            if (itemCache == null) {
                return null
            }
            return itemCache
        }

        override fun update(text: String, i: Boolean) {
            var correct = dataSources.item(text)!!.correct
            var incorrect = dataSources.item(text)!!.incorrect

            if (i)
                dataSources.update(
                    ItemCacheDiv(
                        dataSources.item(text)!!.id,
                        text,
                        correct + 1,
                        incorrect
                    )
                )
            else
                dataSources.update(
                    ItemCacheDiv(
                        dataSources.item(text)!!.id,
                        text,
                        correct,
                        incorrect + 1
                    )
                )
        }

        fun clear_table() {
            dataSources.clear_table()
        }
    }
}