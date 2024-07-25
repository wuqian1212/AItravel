<template>
  <div class="content">
    <div class="leftSide">
      <img src="../components/icons/sidebar.png" alt="" class="l_icon">
      <img src="../components/icons/write.png" alt="" class="r_icon">
    </div>
    <div class="rightSide">
      <deep-chat 
        ref="deepChat"
        style="width: 1250px; height: 800px; border: 0;" 
        camera="true" 
        microphone="true" 
        :connect="connectOptions"
        v-if="deepChatLoaded"
      ></deep-chat>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, nextTick } from 'vue';
import 'deep-chat';

const connectOptions = ref({
  url: '/api/chat/message',
  method: 'POST',
  headers: {
    "Content-Type": "application/json"
  },
  credentials: 'same-origin',
  
});

const deepChatRef = ref(null);
const deepChatLoaded = ref(false);

onMounted(async () => {
  await nextTick();
  deepChatLoaded.value = true;
  await nextTick();
  console.log("ccfffff:" + deepChatRef.value)
  if (true) {  
    sendMessage({
      afterIp: 'chat-messages',
      username: 'user1',
      conversationID: '',
      content: '肖晨曦是谁',
      id: 3
    });
  }
  console.log(deepChatRef.value)
});

const sendMessage = (message) => {
  console.log('Sending message:', message); // 调试信息
  fetch(connectOptions.value.url, {
    method: connectOptions.value.method,
    headers: connectOptions.value.headers,
    body: JSON.stringify(message),
    credentials: connectOptions.value.credentials,
  })
  .then(response => response.json())
  .then(data => {
    console.log('Response from server:', data);
  })
  .catch(error => {
    console.error('Error sending message:', error);
  });
};

const handleMessage = (event) => {
  const { content } = event.detail;
  console.log('Message from DeepChat:', content);
  sendMessage({
    afterIp: '192.168.0.1', // 您可以根据需要动态设置
    username: 'user1', // 您可以根据需要动态设置
    conversationID: '12345', // 您可以根据需要动态设置
    content: content,
    id: '67890' // 您可以根据需要动态设置
  });
};

const clearMessages = () => {
  if (deepChatRef.value) {
    deepChatRef.value.clearMessages();
  }
};

const scrollToBottom = () => {
  if (deepChatRef.value) {
    deepChatRef.value.scrollToBottom();
  }
};
</script>

<style>
html, body, #app {
  margin: 0;
  padding: 0;
  overflow-x: hidden;
}
.content {
  display: flex;
  position: relative;
  width: 100vw;
  height: 100vh;
}
.content .leftSide {
  position: relative;
  flex: 20%;
  background: rgb(254, 245, 238);
}
.content .rightSide {
  position: relative;
  flex: 80%;
  background: #fff;
}
.l_icon {
  position: absolute;
  width: 40px;
  height: 40px;
  left: 20px;
}
.r_icon {
  position: absolute;
  width: 35px;
  height: 35px;
  right: 20px;
}
</style>