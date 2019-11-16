package ru.skillbranch.devintensive.repositories

import androidx.lifecycle.MutableLiveData
import ru.skillbranch.devintensive.data.managers.CacheManager
import ru.skillbranch.devintensive.models.data.Chat
import ru.skillbranch.devintensive.utils.DataGenerator
import java.util.*

object ChatRepository {
    private val chats = CacheManager.loadChats()

    fun loadChats(): MutableLiveData<List<Chat>> {
        return chats
    }

    fun update(chat: Chat) {
        val copy = chats.value!!.toMutableList()
        val ind = chats.value!!.indexOfFirst{ it.id == chat.id}
        if(ind == -1) return
        copy[ind] = chat
        chats.value = copy
    }

    fun find(chatId: String): Chat? {
        val ind = chats.value!!.indexOfFirst { it.id == chatId }
        return chats.value!!.getOrNull(ind)
    }


    fun archiveChats() : List<Chat>? = chats.value!!.filter{ it.isArchived }

    // кол-во непрочитанных сообщений в архиве чатов
    fun archiveUnreadMesCount() : Int? = archiveChats()?.sumBy { it.unreadableMessageCount()}

    fun archiveLastMessageInfo() : Triple<String?, String?, Date?> {
        val lastMessageChat = archiveChats()?.maxBy{ it.lastMessageDate() ?: Date(0) }
        val lastMessageChatShort = lastMessageChat?.lastMessageShort()

        return Triple(lastMessageChatShort?.first,lastMessageChatShort?.second, lastMessageChat?.lastMessageDate())
    }
}