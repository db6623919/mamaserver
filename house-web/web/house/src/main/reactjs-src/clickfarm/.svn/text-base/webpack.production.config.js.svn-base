var webpack = require('webpack');
var path = require('path');
var uglifyJsPlugin = webpack.optimize.UglifyJsPlugin;
var CopyWebpackPlugin = require('copy-webpack-plugin');

var staticPath = '../../webapp/assets';
var viewFilename= '../../webapp/view/main/clickfarming/clickFarmingIndex.vm'

module.exports = {
  devtool: 'cheap-source-map',
  entry: {
      bundle:path.resolve(__dirname, 'app/main.js')
  },
  output: {
    path: path.resolve(__dirname ,staticPath ),
    publicPath: '/',
    filename: '/js/clickfarm/[name].js'
  },
  module: {
    loaders:[
      { test: /\.js[x]?$/, exclude: '/node_modules/', include: path.resolve(__dirname, 'app'), loader: 'babel-loader' },
      { test: /\.less$/, include: path.resolve(__dirname, 'app'), loader: 'style!css?module!autoprefixer!less' },
      { test: /\.(ttf|otf)$/, loader: 'url-loader?limit=8192&name=fonts/[name].[ext]'},
      { test: /\.(png|jpg)$/, loader: 'file-loader?limit=8192&name=i/clickfarm/[name].[ext]'},
    ]
  },
  resolve: {
    extensions: ['', '.js', '.jsx'],
  },
  plugins: [
    new webpack.optimize.DedupePlugin(),
    new webpack.DefinePlugin({
      'process.env':{
        'NODE_ENV': JSON.stringify('production')
      }
    }),
    new uglifyJsPlugin({
      compress: {
        warnings: false
      },
      output: {
        comments: false,
      }
    }),
    new CopyWebpackPlugin([
      { from: './app/clickFarmingIndex.html', to: path.resolve(__dirname, viewFilename) },
      { from: './app/config.js', to: path.resolve(__dirname ,staticPath + '/js/clickfarm/config.js') },
    ]),
  ]
};
