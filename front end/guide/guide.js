// pages/guide/guide.js
Page({
  data: {
    messages: [],
    inputMessage: '',
    scrollToMessage: '',
    showOperationPanel: false,
  },

  onLoad: function() {
    //模拟一些初始消息
    this.setData({
      messages: [
        { id: 1, content: '你好!', avatar: '/icon/person.png', isSelf: false },
        { id: 2, content: '你好,很高兴认识你!', avatar: '/pages/icon/person.png', isSelf: true },
      ]
    });
    this.getLocation();
  },
  getLocation: function() {
    wx.getLocation({
      type: 'gcj02',
      success: (res) => {
        const latitude = res.latitude;
        const longitude = res.longitude;
        this.getLocationName(latitude, longitude);
      },
      fail: (err) => {
        console.error('获取位置失败', err);
        this.setData({ locationName: '定位失败' });
        if (err.errMsg.indexOf('auth deny') !== -1) {
          this.showLocationAuthModal();
        }
      }
    });
  },
  getLocationName: function(latitude, longitude) {
    wx.request({
      url: `https://apis.map.qq.com/ws/geocoder/v1/?location=${latitude},${longitude}&key=YOUR_KEY_HERE`,
      success: (res) => {
        if (res.data.status === 0) {
          const locationName = res.data.result.address_component.city;
          this.setData({ locationName });
        } else {
          this.setData({ locationName: '未知位置' });
        }
      },
      fail: () => {
        this.setData({ locationName: '获取失败' });
      }
    });
  },

  showLocation: function() {
    // 这里可以保留之前的打开地图功能，或者改为刷新位置
    this.getLocation();
  },

  showLocationAuthModal: function() {
    wx.showModal({
      title: '需要位置权限',
      content: '需要获取您的位置信息，是否去设置打开？',
      success: (res) => {
        if (res.confirm) {
          wx.openSetting({
            success: (res) => {
              if (res.authSetting['scope.userLocation']) {
                this.getLocation();
              }
            }
          });
        }
      }
    });
  },

  onInputChange: function(e) {
    this.setData({
      inputMessage: e.detail.value
    });
  },

  sendMessage: function() {
    if (!this.data.inputMessage.trim()) return;
  
    const newMessage = {
      id: this.data.messages.length + 1,
      content: this.data.inputMessage,
      avatar: '/icon/person.png',
      isSelf: true
    };
  
    this.setData({
      messages: [...this.data.messages, newMessage],
      inputMessage: '',
      scrollToMessage: `msg-${newMessage.id}`
    });
  
    // 发送消息到后端
    this.sendMessageToBackend(newMessage.content);
  },
  
  sendMessageToBackend: function(message) {
    const app = getApp();
    wx.request({
      url: app.globalData.globalurl+'/user/updatePassword',
      method: 'POST',
      success: (res) => {
        console.log(message)
        console.log('消息发送成功:', res.data);
        // 如果后端返回了响应，你可以在这里处理
        // 例如，显示机器人的回复
        if (res.data) {
          this.addBotReply(res.data);
        }
      },
      fail: (error) => {
        console.error('消息发送失败:', error);
        wx.showToast({
          title: '发送失败，请重试',
          icon: 'none'
        });
      }
    });
  },
  toggleOperationPanel: function() {
    this.setData({
      showOperationPanel: !this.data.showOperationPanel
    });
  },

  goBack: function() {
    wx.navigateBack({
      delta: 1
    });
  },

  showLocation: function() {
    wx.showToast({
      title: '显示位置信息',
      icon: 'none'
    });
    // 这里可以添加显示位置信息的逻辑
  },
  addBotReply: function(replyContent) {
    // 创建新的机器人消息对象
    const botReply = {
      id: this.data.messages.length + 1, // 给新消息一个唯一的 ID
      content: replyContent, // 机器人回复的内容
      avatar: '/icon/person.png', // 机器人的头像图片路径，请确保路径正确
      isSelf: false // 标记为非用户消息
    };
  
    // 将新的机器人消息添加到消息列表中
    this.setData({
      messages: [...this.data.messages, botReply],
      scrollToMessage: `msg-${botReply.id}` // 设置滚动到新消息
    });
  
    // 可选：如果你想在添加消息后执行一些操作，可以在这里添加
    // 例如，滚动到最新消息
    this.scrollToBottom();
  },
  
  // 辅助函数：滚动到底部
  scrollToBottom: function() {
    wx.createSelectorQuery().select('#chat-container').boundingClientRect(rect => {
      wx.pageScrollTo({
        scrollTop: rect.bottom,
        duration: 300 // 滚动动画持续时间，单位为 ms
      });
    }).exec();
  }
  
});
