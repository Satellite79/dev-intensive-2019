package ru.skillbranch.devintensive.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.skillbranch.devintensive.extensions.mutableLiveData
import ru.skillbranch.devintensive.extensions.shortFormat
import ru.skillbranch.devintensive.models.data.Chat
import ru.skillbranch.devintensive.models.data.ChatItem
import ru.skillbranch.devintensive.models.data.ChatType
import ru.skillbranch.devintensive.repositories.ChatRepository
import ru.skillbranch.devintensive.utils.DataGenerator

class MainViewModel : ViewModel() {
    private val query = mutableLiveData("")
    private val chatRepository = ChatRepository
    private val chats = Transformations.map(chatRepository.loadChats()){chats ->
       return@map toChatItems(chats)
           /*chats.filter { !it.isArchived }
               .map { it.toChatItem() }
               .sortedBy { it.id.toInt() }*/

    }


    fun getChatData() : LiveData<List<ChatItem>> {
        val result = MediatorLiveData<List<ChatItem>>()

        val filterF = {
            val queryStr = query.value!!
            val chatItems = chats.value

            if (chats.value != null)
                result.value = if(queryStr.isEmpty()) chatItems
                           else chatItems!!.filter{ it.title.contains(queryStr, ignoreCase = true)}
        }

        result.addSource(query){filterF.invoke()}
        result.addSource(chats){filterF.invoke()}

        return result
        //return chats
    }

    fun addToArchive(chatId: String) {
        val chat = chatRepository.find(chatId)
        chat ?: return
        chatRepository.update(chat.copy(isArchived = true))
    }


    fun restoreFromArchive(chatId: String) {
        val chat = chatRepository.find(chatId)
        chat ?: return
        chatRepository.update(chat.copy(isArchived = false))
    }

    fun handleSearchQuery(text: String) {
        query.value = text
    }

    private fun toChatItems(chats: List<Chat>) : List<ChatItem> {
        var chatItems = chats.filter { !it.isArchived }
                .map { it.toChatItem() }.toMutableList()

        if (chats.any { it.isArchived == true }) {
            val archiveLastMessageInfo = ChatRepository.archiveLastMessageInfo()
            chatItems.add(
                ChatItem(
                    "-1",
                    null,
                    "",
                    "Архив чатов",
                    archiveLastMessageInfo.first,
                    ChatRepository.archiveUnreadMesCount() ?: 0,
                    archiveLastMessageInfo.third?.shortFormat() ?: "",
                    false,
                    ChatType.ARCHIVE,
                    archiveLastMessageInfo.second
                )
            )
        }

        return chatItems.sortedBy { it.id.toInt() }
    }
}