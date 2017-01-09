var webpack = require('webpack');
var path = require('path');
var OpenBrowserPlugin = require('open-browser-webpack-plugin');
var PORT = 8888


module.exports = {
  devServer: {
    historyApiFallback: true,
    hot: true,
    inline: true,
    progress: true,
    contentBase: './app',
    port: PORT
  },
  entry: [
    'webpack/hot/dev-server',
    'webpack-dev-server/client?http://192.168.10.169:'+PORT,
    path.resolve(__dirname, 'app/main.js')
  ],
  output: {
    path: __dirname + '/build',
    publicPath: '/',
    filename: './bundle.js'
  },
  module: {
    loaders:[
      { test: /\.js[x]?$/, include: path.resolve(__dirname, 'app'), exclude: /node_modules/, loader: 'babel-loader' },
      { test: /\.less$/, include: path.resolve(__dirname, 'app'), loader: 'style!css?module!autoprefixer!less' },
      { test: /\.(ttf|otf)$/, loader: 'url-loader?limit=8192&name=font/[name].[ext]'}
    ]
  },
  resolve: {
    extensions: ['', '.js', '.jsx'],
  },
  plugins: [
    new webpack.HotModuleReplacementPlugin(),
    new OpenBrowserPlugin({ url: 'http://192.168.10.169:'+PORT })
  ]
};
