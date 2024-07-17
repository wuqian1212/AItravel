

// pages/setUp/setUp.js
Page({
  data: {
    showLogoutConfirm: false
  },

  navigateToChangePhone: function() {
    wx.navigateTo({
      url: '/pages/changePhone/changePhone'
    });
  },

  navigateToChangePassword: function() {
    wx.navigateTo({
      url: '/pages/changePassword/changePassword'
    });
  },

  showLogoutConfirm: function() {
    this.setData({
      showLogoutConfirm: true
    });
  },

  cancelLogout: function() {
    this.setData({
      showLogoutConfirm: false
    });
  },

  confirmLogout: function() {
    
    // 执行退出登录的逻辑
    wx.removeStorageSync('userInfo');
    wx.removeStorageSync('isLoggedIn');
    
    this.setData({
      showLogoutConfirm: false
    });

    wx.showToast({
      title: '已退出登录',
      icon: 'success',
      duration: 2000
    });

    // 退出后跳转到登录页或首页
    wx.reLaunch({
      url: '/pages/user/user'
    });
  }
});