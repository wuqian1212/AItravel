// pages/user/user.js
Page({
  data: {
    isLoggedIn: false,
    userInfo: {
      header: '/icon/person.png',
      username: '',
      id: '',
      phone:''
    }
  },

  onLoad: function() {
    this.checkLoginStatus();
  },

  onShow: function() {
    // 只在必要时更新登录状态
    if (this.data.isLoggedIn !== wx.getStorageSync('isLoggedIn')) {
      this.checkLoginStatus();
    }
  },

  checkLoginStatus: function() {
    try {
      const isLoggedIn = wx.getStorageSync('isLoggedIn');
      console.log(isLoggedIn)
      if (isLoggedIn) {
        const userInfo = wx.getStorageSync('userInfo');
        this.setData({
          isLoggedIn: true,
          userInfo: userInfo || this.data.userInfo
        });

      } else {
        this.setData({
          isLoggedIn: false,
          userInfo: {
            header: '/icon/person.png',
            userName: '用户名',
            userId: ''
          }
        });
      }
    } catch (e) {
      console.error('Error checking login status:', e);
    }
  },
  handleAvatarTap: function() {
    if (!this.data.isLoggedIn) return;
  
    wx.chooseMedia({
      count: 1,
      mediaType: ['image'],
      sourceType: ['album', 'camera'],
      camera: 'back',
      success: (res) => {
        const tempFilePath = res.tempFiles[0].tempFilePath;
        const fileSize = res.tempFiles[0].size;
        const maxSize = 5 * 1024 * 1024; // 5MB
  
        if (fileSize > maxSize) {
          wx.showToast({
            title: '图片太大，请选择5MB以下的图片',
            icon: 'none'
          });
          return;
        }
  
        // 预览和确认
        wx.previewImage({
          current: tempFilePath,
          urls: [tempFilePath],
          success: () => {
            wx.showModal({
              title: '确认上传',
              content: '是否使用此图片作为新的头像？',
              success: (res) => {
                if (res.confirm) {
                  this.uploadAvatar(tempFilePath);
                }
              }
            });
          }
        });
      },
      fail: (err) => {
        console.error('选择媒体失败', err);
        if (err.errMsg !== "chooseMedia:fail cancel") {
          wx.showToast({
            title: '选择图片失败，请重试',
            icon: 'none'
          });
        }
      }
    });
  },
  
  uploadAvatar: function(filePath) {
    wx.showLoading({ title: '上传中...' });
  
    const uploadTask = wx.uploadFile({
      url: 'https://your-api-url.com/updateAvatar',
      filePath: filePath,
      name: 'avatar',
      formData: {
        'userId': this.data.userInfo.id
      },
      success: (res) => {
        const data = JSON.parse(res.data);
        if (data.success) {
          const userInfo = this.data.userInfo;
          userInfo.avatarUrl = data.avatarUrl; // 使用服务器返回的URL
          this.setData({ userInfo: userInfo });
          wx.setStorageSync('userInfo', userInfo);
          wx.showToast({ title: '头像更新成功', icon: 'success' });
        } else {
          wx.showToast({ title: '头像更新失败', icon: 'none' });
        }
      },
      fail: (error) => {
        console.error('上传失败', error);
        wx.showToast({ title: '网络错误，请重试', icon: 'none' });
      },
      complete: () => {
        wx.hideLoading();
      }
    });
  
    uploadTask.onProgressUpdate((res) => {
      console.log('上传进度', res.progress);
      // 可以在这里更新进度条
    });
  
    // 添加取消按钮
    wx.showModal({
      title: '上传中',
      content: '是否取消上传？',
      success: (res) => {
        if (res.confirm) {
          uploadTask.abort();
          wx.hideLoading();
          wx.showToast({ title: '已取消上传', icon: 'none' });
        }
      }
    });
  },


  navigateToLogin: function() {
    wx.navigateTo({
      url: '/pages/login/login'
    });
  },

  navigateTo: function(e) {
    const page = e.currentTarget.dataset.page;
    const routes = {
      bill: '/pages/bill/bill',
      favorites: '/pages/favorites/favorites',
      history: '/pages/history/history',
      settings: '/pages/setUp/setUp',
      customerService: '/pages/customerService/customerService',
      aboutUs: '/pages/aboutUs/aboutUs'
    };

    if (routes[page]) {
      wx.navigateTo({
        url: routes[page]
      });
    } else {
      console.error('Unknown page:', page);
    }
  }
});