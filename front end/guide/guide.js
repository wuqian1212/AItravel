// pages/guide/guide.js
Page({
  data: {
    messages: [],
    inputMessage: '',
    scrollToMessage: '',
    showOperationPanel: false,
  },

  onLoad: function() {
    // 模拟一些初始消息
    this.setData({
      messages: [
        { id: 1, content: '你好!', avatar: '/icon/person.png', isSelf: false },
        { id: 2, content: '你好,很高兴认识你!', avatar: '/icon/person.png', isSelf: true },
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

    // 模拟接收回复
    setTimeout(() => {
      const replyMessage = {
        id: this.data.messages.length + 1,
        content: '收到你的消息了!',
        avatar: '/icon/person.png',
        isSelf: false
      };

      this.setData({
        messages: [...this.data.messages, replyMessage],
        scrollToMessage: `msg-${replyMessage.id}`
      });
    }, 1000);
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
  }
});

// Page({

//   /**
//    * 页面的初始数据
//    */
//   data: {

//   },

//   /**
//    * 生命周期函数--监听页面加载
//    */
//   onLoad(options) {

//   },

//   /**
//    * 生命周期函数--监听页面初次渲染完成
//    */
//   onReady() {

//   },

//   /**
//    * 生命周期函数--监听页面显示
//    */
//   onShow() {

//   },

//   /**
//    * 生命周期函数--监听页面隐藏
//    */
//   onHide() {

//   },

//   /**
//    * 生命周期函数--监听页面卸载
//    */
//   onUnload() {

//   },

//   /**
//    * 页面相关事件处理函数--监听用户下拉动作
//    */
//   onPullDownRefresh() {

//   },

//   /**
//    * 页面上拉触底事件的处理函数
//    */
//   onReachBottom() {

//   },

//   /**
//    * 用户点击右上角分享
//    */
//   onShareAppMessage() {

//   }
// })