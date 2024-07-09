// pages/home/home.js
Page({
  data: {
    current: 0,
    indicatorDots: true,
    autoplay: true,
    interval: 3000,
    duration: 500,
    circular: true,
    latitude: null,
    longitude: null,
    images: [
      '/pages/icon/总的.png',
      '/pages/icon/智能路线规划.png',
      '/pages/icon/实时语音导览.png',
      '/pages/icon/旅游贴士.png',
      '/pages/icon/朋友圈文案生成.png',
      '/pages/icon/美颜功能.png',
      '/pages/icon/周边推荐.png'
    ],
    travelTips: [
      "记得带好防晒霜！",
      "多喝水，保持水分充足！",
      "拍照记录美好时刻！",
      "尊重当地文化和习俗。",
      "提前查看天气预报。"
    ],
    navItems: [
      { page: 'shopping', icon: '/images/icons/shopping.png', text: '购物' },
      { page: 'food', icon: '/images/icons/food.png', text: '美食' },
      { page: 'hotel', icon: '/images/icons/hotel.png', text: '酒店' },
      { page: 'transport', icon: '/images/icons/transport.png', text: '交通' },
      { page: 'community', icon: '/images/icons/community.png', text: '社区' }
    ],
    latitude: 23.099994,
    longitude: 113.324520,
  },
  onLoad: function() {
    // 页面加载时自动获取位置
    this.getCurrentLocation();
  },

  getCurrentLocation: function() {
    wx.getLocation({
      type: 'gcj02', // 返回可以用于wx.openLocation的经纬度
      success: (res) => {
        const latitude = res.latitude
        const longitude = res.longitude
        this.setData({
          latitude: latitude,
          longitude: longitude
        });
        console.log(`当前位置：纬度 ${latitude}，经度 ${longitude}`);
        
         // 调用反向地理编码API获取位置描述
      that.getLocationName(latitude, longitude);
      },
      fail: (err) => {
        console.error('获取位置失败', err);
        wx.showToast({
          title: '获取位置失败，请检查位置权限设置',
          icon: 'none'
        });
      }
    })
  },

  // 如果需要在地图上显示位置，可以添加这个方法
  showLocationOnMap: function(latitude, longitude) {
    wx.openLocation({
      latitude,
      longitude,
      scale: 18
    })
  },

  // 轮播图 
  swiperChange(e) {
    this.setData({
      current: e.detail.current
    });
  },

  prevImage() {
    let current = this.data.current;
    current = (current - 1 + this.data.images.length) % this.data.images.length;
    this.setData({ current });
  },

  nextImage() {
    let current = this.data.current;
    current = (current + 1) % this.data.images.length;
    this.setData({ current });
  },
  onLoad(options) {
    this.requestLocationPermission();
  },

  
  
  // 页面导航
  navigateTo(event) {
    const page = event.currentTarget.dataset.page;
    wx.navigateTo({
      url: `/pages/${page}/${page}`
    });
  },

  openAIGuide() {
    wx.navigateTo({
      url: '/pages/ai-guide/ai-guide'
    });
  },

  // 地图相关
  regionChange(event) {
    this.updateMapLocation(event.detail.region);
  },

  updateMapLocation(region) {
    // 实现更新地图标记的逻辑
  },

  // 权限请求
  requestRecordPermission() {
    wx.authorize({
      scope: 'scope.record',
      success: () => console.log('录音权限已授权'),
      fail: () => this.showPermissionModal('录音')
    });
  },

  requestLocationPermission() {
    wx.getSetting({
      success: (res) => {
        if (!res.authSetting['scope.userLocation']) {
          wx.authorize({
            scope: 'scope.userLocation',
            success: () => this.getLocation(),
            fail: () => this.showPermissionModal('位置')
          });
        } else {
          this.getLocation();
        }
      }
    });
  },

  showPermissionModal(permissionType) {
    wx.showModal({
      title: '授权提醒',
      content: `需要${permissionType}权限才能使用该功能，是否去设置页面开启？`,
      success: (res) => {
        if (res.confirm) {
          wx.openSetting();
        }
      }
    });
  },

  // 位置服务
  getLocation() {
    wx.getLocation({
      type: 'gcj02',
      success: (res) => {
        const { latitude, longitude } = res;
        this.setData({ latitude, longitude });
        this.updateMapCenter();
      },
      fail: (err) => {
        console.error('获取位置失败', err);
        wx.showToast({
          title: '获取位置失败',
          icon: 'none'
        });
      }
    });
  },

  updateMapCenter() {
    const mapCtx = wx.createMapContext('myMap');
    mapCtx.moveToLocation();
  },

  // 功能方法
  showTravelTips() {
    const tips = this.data.travelTips[Math.floor(Math.random() * this.data.travelTips.length)];
    wx.showToast({
      title: tips,
      icon: 'none',
      duration: 3000
    });
  },

  generateFriendCircleCopy() {
    const place = "美丽的海滩";
    const time = new Date().toLocaleDateString('zh-CN', { year: 'numeric', month: 'long', day: 'numeric' });
    const copy = `在${place}度过了美好的一天！#旅行日记# ${time}`;
    wx.setClipboardData({
      data: copy,
      success: () => {
        wx.showToast({
          title: '文案已复制到剪贴板',
          icon: 'success'
        });
      }
    });
  },

  chooseImage() {
    wx.chooseImage({
      count: 1,
      sizeType: ['original', 'compressed'],
      sourceType: ['album', 'camera'],
      success: (res) => {
        const tempFilePaths = res.tempFilePaths;
        // 这里可以添加图片处理逻辑
        console.log('选择的图片路径:', tempFilePaths);
      }
    });
  },

  onSearch(e) {
    const searchValue = e.detail.value.trim();
    if (searchValue) {
      console.log('搜索:', searchValue);
      // 实现搜索逻辑
    }
  },

  onLocationTap() {
    this.getLocation();
  },

  // 生命周期函数
  onReady() {
    // 页面首次渲染完毕时执行
  },

  onShow() {
    // 页面显示时执行
  },

  onHide() {
    // 页面隐藏时执行
  },

  onUnload() {
    // 页面卸载时执行
  },

  onPullDownRefresh() {
    // 下拉刷新时执行
    // 记得调用 wx.stopPullDownRefresh()
  },

  onReachBottom() {
    // 上拉触底时执行
  },

  onShareAppMessage() {
    // 用户点击右上角分享时执行
    return {
      title: '分享标题',
      path: '/pages/home/home'
    };
  }
});
