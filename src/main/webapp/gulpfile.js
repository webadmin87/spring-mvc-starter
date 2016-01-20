var gulp = require('gulp'), // Gulp JS
    csso = require('gulp-csso'), // Минификация CSS
    uglify = require('gulp-uglify'), // Минификация JS
    concat = require('gulp-concat'), // Склейка файлов
    compass = require('gulp-compass'); // Компас

var srcDir = './resources/src';

var jsBuildDir = './resources/build/js';
var templatesBuildDir = './resources/build/templates';
var imagesBuildDir = './resources/build/images';
var cssBuildDir = './resources/build/css';

gulp.task('jsConcat', function() {

    var src = [srcDir+'/js/app.js', srcDir+'/js/*Service.js', srcDir+'/js/*.js'];

    gulp.src(src)
        .pipe(concat('packed.js'))
        .pipe(gulp.dest(jsBuildDir))

    gulp.src(src)
        .pipe(concat('packed.min.js'))
        .pipe(uglify())
        .pipe(gulp.dest(jsBuildDir))

});

gulp.task('jsTemplates', function() {
    gulp.src([srcDir+'/templates/**'])
        .pipe(gulp.dest(templatesBuildDir))
});

gulp.task('images', function() {
    gulp.src([srcDir+'/images/**'])
        .pipe(gulp.dest(imagesBuildDir))
});

gulp.task('cssMinify', function() {
    gulp.src([srcDir+'/css/*.css'])
        .pipe(csso())
        .pipe(gulp.dest(cssBuildDir))
});

/*
var compassProject = srcDir+'/themes/demo';

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

gulp.task('default', ['jsConcat', 'jsTemplates', 'images', 'cssMinify']);

gulp.task('watch', function() {
    gulp.watch(srcDir+'/js/*.js', ['jsConcat']);
    gulp.watch(srcDir+'/templates/**', ['jsTemplates']);
    gulp.watch(srcDir+'/images/**', ['images']);
    gulp.watch(srcDir+'/css/*.css', ['cssMinify']);
    //gulp.watch([compassProject+'/sass/*.sass', compassProject+'/sass/*.scss'], ['compass']);
});
