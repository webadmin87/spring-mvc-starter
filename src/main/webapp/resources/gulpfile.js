var gulp = require('gulp'), // Gulp JS
    csso = require('gulp-csso'), // Минификация CSS
    uglify = require('gulp-uglify'), // Минификация JS
    concat = require('gulp-concat'), // Склейка файлов
    compass = require('gulp-compass'), // Компас
    browserify = require('browserify'),
    babelify = require('babelify'),
    source = require('vinyl-source-stream');

var srcDir = './';
var appDir = './app';
var buildDir = './dist';

gulp.task('build', function () {
    return browserify(
        {
            entries: appDir+'/router.jsx', extensions: ['.js', '.jsx'],
            paths: ["./node_modules", "./app"], debug: true
        })
        .transform('babelify', {presets: ['es2015', 'react']})
        .bundle()
        .pipe(source('bundle.js'))
        .pipe(gulp.dest(buildDir));
});


gulp.task('images', function() {
    gulp.src([srcDir+'/images/**'])
        .pipe(gulp.dest(buildDir+"/images/"))
});


gulp.task('css', function() {
    gulp.src([srcDir+'/css/*.css'])
        .pipe(csso())
        .pipe(gulp.dest(buildDir+"/css/"))
});


/*
var compassProject = srcDir+'/path/to/project';
gulp.task('compass', function() {
    gulp.src(compassProject+'/sass/screen.sass')
        .pipe(compass({
            config_file: compassProject+'/config.rb',
            css: compassProject+'/css',
            sass: compassProject+'/sass',
            image: compassProject+'/img'
        }))
        .pipe(gulp.dest(compassProject+'/css'));
});
*/

gulp.task('default', ['build', 'images', 'css']);

gulp.task('watch', function() {
    gulp.watch(appDir+'/**', ['build']);;
    gulp.watch(srcDir+'/images/**', ['images']);
    gulp.watch(srcDir+'/css/*.css', ['css']);
    //gulp.watch([compassProject+'/sass/*.sass', compassProject+'/sass/*.scss'], ['compass']);
});
