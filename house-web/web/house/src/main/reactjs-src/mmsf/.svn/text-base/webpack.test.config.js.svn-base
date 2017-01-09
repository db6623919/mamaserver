var webpack = require('webpack');
var path = require('path');
var uglifyJsPlugin = webpack.optimize.UglifyJsPlugin;
var CopyWebpackPlugin = require('copy-webpack-plugin');


module.exports = {
  devtool: 'cheap-source-map',
  entry: {
      bundle:path.resolve(__dirname, 'app/main.js')
  },
  output: {
    path: __dirname  +'/build',
    publicPath: '/',
    filename: './[name].js'
  },
  module: {
    loaders:[
      { test: /\.js[x]?$/, exclude: '/node_modules/', include: path.resolve(__dirname, 'app'), loader: 'babel-loader' },
      { test: /\.less$/, include: path.resolve(__dirname, 'app'), loader: 'style!css?module!autoprefixer!less' },
      { test: /\.(ttf|otf)$/, loader: 'url-loader?limit=8192&name=fonts/[name].[ext]'}
    ]
  },
  resolve: {
    extensions: ['', '.js', '.jsx'],
  },
  plugins: [
    new webpack.optimize.DedupePlugin(),
    new uglifyJsPlugin({
      compress: {
        warnings: false
      }
    }),
    new CopyWebpackPlugin([
      { from: './app/index.html', to: 'index.html'},
      { from: './app/config.js', to: 'config.js' },
    ]),
  ]
};
