const path = require('path')
const webpack = require('webpack')

const resolve = file => path.resolve(__dirname, file)

module.exports = {   //具体参照vue-cli 3.0的打包方式
  publicPath: './',  //基本路径
  lintOnSave: true,  //是否在保存的时候检查
  chainWebpack: config => { //webpack-chain链式API的调用方式,简化webpack配置的修改(链式操作)
     config.resolve.alias.set('@', resolve('src')) // key,value自行定义，比如.set('@@', resolve('src/components'))) //配置目录别名
  },
  css: {
    loaderOptions: {  //css启用模块
      stylus: {
        import: [resolve('./src/index.styl')]
      }
    }
  },
  transpileDependencies: ['mand-mobile'], //默认情况下 babel-loader 会忽略所有 node_modules 中的文件。如果你想要通过 Babel 显式转译一个依赖，可以在这个选项中列出来
  pluginOptions: {  //第三方插件配置
    'style-resources-loader': {
      preProcessor: 'stylus',
      patterns: [resolve('src/assets/*.styl')]
    }
  },
  configureWebpack: { //配置项 
    // performance:{//2020-4-20
    //   hints:false
    // },
    //  //警告 webpack 的性能提示
    //  performance: { //2020-4-20
    //   hints:'warning',
    //   //入口起点的最大体积
    //   maxEntrypointSize: 50000000,
    //   //生成文件的最大体积
    //   maxAssetSize: 30000000,
    //   //只给出 js 文件的性能提示
    //   assetFilter: function(assetFilename) {
    //     return assetFilename.endsWith('.js');
    //   }
    // },
    // plugins: [
    //   new webpack.ProvidePlugin({
    //     $: 'jquery',
    //     jQuery: 'jquery',
    //     'windows.jQuery': 'jquery'
    //   })
    // ],
    externals: { //引入插件
      Eleditor: 'Eleditor',
      jQuery: 'jquery'
    }
  }
}
