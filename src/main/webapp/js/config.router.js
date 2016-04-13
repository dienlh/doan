'use strict';

/**
 * Config for the router
 */
angular.module('app')
  .run(
    [          '$rootScope', '$state', '$stateParams',
      function ($rootScope,   $state,   $stateParams) {
          $rootScope.$state = $state;
          $rootScope.$stateParams = $stateParams;        
      }
    ]
  )
  .config(
    [          '$stateProvider', '$urlRouterProvider',
      function ($stateProvider,   $urlRouterProvider) {
          
          $urlRouterProvider
              .otherwise('/app/dashboard-v1');
          $stateProvider
              .state('app', {
                  abstract: true,
                  url: '/app',
                  templateUrl: 'tpl/app.html'
              })
              .state('app.dashboard-v1', {
                  url: '/dashboard-v1',
                  templateUrl: 'tpl/app_dashboard_v1.html',
                  resolve: {
                    deps: ['$ocLazyLoad',
                      function( $ocLazyLoad ){
                        return $ocLazyLoad.load(['js/controllers/chart.js']);
                    }]
                  }
              })
              .state('app.dashboard-v2', {
                  url: '/dashboard-v2',
                  templateUrl: 'tpl/app_dashboard_v2.html',
                  resolve: {
                    deps: ['$ocLazyLoad',
                      function( $ocLazyLoad ){
                        return $ocLazyLoad.load(['js/controllers/chart.js']);
                    }]
                  }
              })
              .state('app.ui', {
                  url: '/ui',
                  template: '<div ui-view class="fade-in-up"></div>'
              })
              .state('app.ui.buttons', {
                  url: '/buttons',
                  templateUrl: 'tpl/ui_buttons.html'
              })
              .state('app.ui.icons', {
                  url: '/icons',
                  templateUrl: 'tpl/ui_icons.html'
              })
              .state('app.ui.grid', {
                  url: '/grid',
                  templateUrl: 'tpl/ui_grid.html'
              })
              .state('app.ui.widgets', {
                  url: '/widgets',
                  templateUrl: 'tpl/ui_widgets.html'
              })          
              .state('app.ui.bootstrap', {
                  url: '/bootstrap',
                  templateUrl: 'tpl/ui_bootstrap.html'
              })
              .state('app.ui.sortable', {
                  url: '/sortable',
                  templateUrl: 'tpl/ui_sortable.html'
              })
              .state('app.ui.portlet', {
                  url: '/portlet',
                  templateUrl: 'tpl/ui_portlet.html'
              })
              .state('app.ui.timeline', {
                  url: '/timeline',
                  templateUrl: 'tpl/ui_timeline.html'
              })
              .state('app.ui.tree', {
                  url: '/tree',
                  templateUrl: 'tpl/ui_tree.html',
                  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad ){
                          return $ocLazyLoad.load('angularBootstrapNavTree').then(
                              function(){
                                 return $ocLazyLoad.load('js/controllers/tree.js');
                              }
                          );
                        }
                      ]
                  }
              })
              .state('app.ui.toaster', {
                  url: '/toaster',
                  templateUrl: 'tpl/ui_toaster.html',
                  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad){
                          return $ocLazyLoad.load('toaster').then(
                              function(){
                                 return $ocLazyLoad.load('js/controllers/toaster.js');
                              }
                          );
                      }]
                  }
              })
              .state('app.ui.jvectormap', {
                  url: '/jvectormap',
                  templateUrl: 'tpl/ui_jvectormap.html',
                  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad){
                          return $ocLazyLoad.load('js/controllers/vectormap.js');
                      }]
                  }
              })
              .state('app.ui.googlemap', {
                  url: '/googlemap',
                  templateUrl: 'tpl/ui_googlemap.html',
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad ){
                          return uiLoad.load( [
                            'js/app/map/load-google-maps.js',
                            'js/app/map/ui-map.js',
                            'js/app/map/map.js'] ).then(
                              function(){
                                return loadGoogleMaps(); 
                              }
                            );
                      }]
                  }
              })
              .state('app.chart', {
                  url: '/chart',
                  templateUrl: 'tpl/ui_chart.html',
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad){
                          return uiLoad.load('js/controllers/chart.js');
                      }]
                  }
              })
              // table
              .state('app.table', {
                  url: '/table',
                  template: '<div ui-view></div>'
              })
              .state('app.table.static', {
                  url: '/static',
                  templateUrl: 'tpl/table_static.html'
              })
              .state('app.table.datatable', {
                  url: '/datatable',
                  templateUrl: 'tpl/table_datatable.html'
              })
              .state('app.table.footable', {
                  url: '/footable',
                  templateUrl: 'tpl/table_footable.html'
              })
              .state('app.table.grid', {
                  url: '/grid',
                  templateUrl: 'tpl/table_grid.html',
                  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad ){
                          return $ocLazyLoad.load('ngGrid').then(
                              function(){
                                  return $ocLazyLoad.load('js/controllers/grid.js');
                              }
                          );
                      }]
                  }
              })
              // form
              .state('app.form', {
                  url: '/form',
                  template: '<div ui-view class="fade-in"></div>',
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad){
                          return uiLoad.load('js/controllers/form.js');
                      }]
                  }
              })
              .state('app.form.elements', {
                  url: '/elements',
                  templateUrl: 'tpl/form_elements.html'
              })
              .state('app.form.validation', {
                  url: '/validation',
                  templateUrl: 'tpl/form_validation.html'
              })
              .state('app.form.wizard', {
                  url: '/wizard',
                  templateUrl: 'tpl/form_wizard.html'
              })
              .state('app.form.fileupload', {
                  url: '/fileupload',
                  templateUrl: 'tpl/form_fileupload.html',
                  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad){
                          return $ocLazyLoad.load('angularFileUpload').then(
                              function(){
                                 return $ocLazyLoad.load('js/controllers/file-upload.js');
                              }
                          );
                      }]
                  }
              })
              .state('app.form.imagecrop', {
                  url: '/imagecrop',
                  templateUrl: 'tpl/form_imagecrop.html',
                  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad){
                          return $ocLazyLoad.load('ngImgCrop').then(
                              function(){
                                 return $ocLazyLoad.load('js/controllers/imgcrop.js');
                              }
                          );
                      }]
                  }
              })
              .state('app.form.select', {
                  url: '/select',
                  templateUrl: 'tpl/form_select.html',
                  controller: 'SelectCtrl',
                  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad ){
                          return $ocLazyLoad.load('ui.select').then(
                              function(){
                                  return $ocLazyLoad.load('js/controllers/select.js');
                              }
                          );
                      }]
                  }
              })
              .state('app.form.slider', {
                  url: '/slider',
                  templateUrl: 'tpl/form_slider.html',
                  controller: 'SliderCtrl',
                  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad ){
                          return $ocLazyLoad.load('vr.directives.slider').then(
                              function(){
                                  return $ocLazyLoad.load('js/controllers/slider.js');
                              }
                          );
                      }]
                  }
              })
              .state('app.form.editor', {
                  url: '/editor',
                  templateUrl: 'tpl/form_editor.html',
                  controller: 'EditorCtrl',
                  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad ){
                          return $ocLazyLoad.load('textAngular').then(
                              function(){
                                  return $ocLazyLoad.load('js/controllers/editor.js');
                              }
                          );
                      }]
                  }
              })
              // pages
              .state('app.page', {
                  url: '/page',
                  template: '<div ui-view class="fade-in-down"></div>'
              })
              .state('app.page.profile', {
                  url: '/profile',
                  templateUrl: 'tpl/page_profile.html'
              })
              .state('app.page.post', {
                  url: '/post',
                  templateUrl: 'tpl/page_post.html'
              })
              .state('app.page.search', {
                  url: '/search',
                  templateUrl: 'tpl/page_search.html'
              })
              .state('app.page.invoice', {
                  url: '/invoice',
                  templateUrl: 'tpl/page_invoice.html'
              })
              .state('app.page.price', {
                  url: '/price',
                  templateUrl: 'tpl/page_price.html'
              })
              .state('app.docs', {
                  url: '/docs',
                  templateUrl: 'tpl/docs.html'
              })
              // others
              .state('lockme', {
                  url: '/lockme',
                  templateUrl: 'tpl/page_lockme.html'
              })
              .state('access', {
                  url: '/access',
                  template: '<div ui-view class="fade-in-right-big smooth"></div>'
              })
              .state('access.signin', {
                  url: '/signin',
                  templateUrl: 'tpl/page_signin.html',
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad ){
                          return uiLoad.load( ['js/controllers/signin.js'] );
                      }]
                  }
              })
              .state('access.signup', {
                  url: '/signup',
                  templateUrl: 'tpl/page_signup.html',
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad ){
                          return uiLoad.load( ['js/controllers/signup.js'] );
                      }]
                  }
              })
              .state('access.forgotpwd', {
                  url: '/forgotpwd',
                  templateUrl: 'tpl/page_forgotpwd.html'
              })
              .state('access.404', {
                  url: '/404',
                  templateUrl: 'tpl/page_404.html'
              })

              // fullCalendar
              .state('app.calendar', {
                  url: '/calendar',
                  templateUrl: 'tpl/app_calendar.html',
                  // use resolve to load other dependences
                  resolve: {
                      deps: ['$ocLazyLoad', 'uiLoad',
                        function( $ocLazyLoad, uiLoad ){
                          return uiLoad.load(
                            ['vendor/jquery/fullcalendar/fullcalendar.css',
                              'vendor/jquery/fullcalendar/theme.css',
                              'vendor/jquery/jquery-ui-1.10.3.custom.min.js',
                              'vendor/libs/moment.min.js',
                              'vendor/jquery/fullcalendar/fullcalendar.min.js',
                              'js/app/calendar/calendar.js']
                          ).then(
                            function(){
                              return $ocLazyLoad.load('ui.calendar');
                            }
                          )
                      }]
                  }
              })

              // mail
              .state('app.mail', {
                  abstract: true,
                  url: '/mail',
                  templateUrl: 'tpl/mail.html',
                  // use resolve to load other dependences
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad ){
                          return uiLoad.load( ['js/app/mail/mail.js',
                                               'js/app/mail/mail-service.js',
                                               'vendor/libs/moment.min.js'] );
                      }]
                  }
              })
              .state('app.mail.list', {
                  url: '/inbox/{fold}',
                  templateUrl: 'tpl/mail.list.html'
              })
              .state('app.mail.detail', {
                  url: '/{mailId:[0-9]{1,4}}',
                  templateUrl: 'tpl/mail.detail.html'
              })
              .state('app.mail.compose', {
                  url: '/compose',
                  templateUrl: 'tpl/mail.new.html'
              })

              .state('layout', {
                  abstract: true,
                  url: '/layout',
                  templateUrl: 'tpl/layout.html'
              })
              .state('layout.fullwidth', {
                  url: '/fullwidth',
                  views: {
                      '': {
                          templateUrl: 'tpl/layout_fullwidth.html'
                      },
                      'footer': {
                          templateUrl: 'tpl/layout_footer_fullwidth.html'
                      }
                  },
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad ){
                          return uiLoad.load( ['js/controllers/vectormap.js'] );
                      }]
                  }
              })
              .state('layout.mobile', {
                  url: '/mobile',
                  views: {
                      '': {
                          templateUrl: 'tpl/layout_mobile.html'
                      },
                      'footer': {
                          templateUrl: 'tpl/layout_footer_mobile.html'
                      }
                  }
              })
              .state('layout.app', {
                  url: '/app',
                  views: {
                      '': {
                          templateUrl: 'tpl/layout_app.html'
                      },
                      'footer': {
                          templateUrl: 'tpl/layout_footer_fullwidth.html'
                      }
                  },
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad ){
                          return uiLoad.load( ['js/controllers/tab.js'] );
                      }]
                  }
              })
              .state('apps', {
                  abstract: true,
                  url: '/apps',
                  templateUrl: 'tpl/layout.html'
              })
              .state('apps.note', {
                  url: '/note',
                  templateUrl: 'tpl/apps_note.html',
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad ){
                          return uiLoad.load( ['js/app/note/note.js',
                                               'vendor/libs/moment.min.js'] );
                      }]
                  }
              })
              .state('apps.contact', {
                  url: '/contact',
                  templateUrl: 'tpl/apps_contact.html',
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad ){
                          return uiLoad.load( ['js/app/contact/contact.js'] );
                      }]
                  }
              })
              .state('app.weather', {
                  url: '/weather',
                  templateUrl: 'tpl/apps_weather.html',
                  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad ){
                          return $ocLazyLoad.load(
                              {
                                  name: 'angular-skycons',
                                  files: ['js/app/weather/skycons.js',
                                          'vendor/libs/moment.min.js', 
                                          'js/app/weather/angular-skycons.js',
                                          'js/app/weather/ctrl.js' ] 
                              }
                          );
                      }]
                  }
              })
              .state('music', {
                  url: '/music',
                  templateUrl: 'tpl/music.html',
                  controller: 'MusicCtrl',
                  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad ){
                          return $ocLazyLoad.load([
                            'com.2fdevs.videogular', 
                            'com.2fdevs.videogular.plugins.controls', 
                            'com.2fdevs.videogular.plugins.overlayplay',
                            'com.2fdevs.videogular.plugins.poster',
                            'com.2fdevs.videogular.plugins.buffering',
                            'js/app/music/ctrl.js', 
                            'js/app/music/theme.css'
                          ]);
                      }]
                  }
              })
                .state('music.home', {
                    url: '/home',
                    templateUrl: 'tpl/music.home.html'
                })
                .state('music.genres', {
                    url: '/genres',
                    templateUrl: 'tpl/music.genres.html'
                })
                .state('music.detail', {
                    url: '/detail',
                    templateUrl: 'tpl/music.detail.html'
                })
                .state('music.mtv', {
                    url: '/mtv',
                    templateUrl: 'tpl/music.mtv.html'
                })
                .state('music.mtvdetail', {
                    url: '/mtvdetail',
                    templateUrl: 'tpl/music.mtv.detail.html'
                })
                .state('music.playlist', {
                    url: '/playlist/{fold}',
                    templateUrl: 'tpl/music.playlist.html'
                })
                .state('template', {
	                  url: '/template',
	                  templateUrl: 'template/block/layoutWeb.html'
                 })
                .state('template.home', {
	                  url: '/home',
	                  data: {
	                         authorities: [],
	                         pageTitle: 'Trang chủ'
	                  },
	                  templateUrl: 'template/index.html',
	                  controller:'homeCtrl',
	                  resolve: {
	                        deps: ['uiLoad',
	                            function( uiLoad){
	                               return uiLoad.load('js/app/template/home.js');
	                        }]
	                  }
                 })
                .state('template.services', {
	                  url: '/services',
	                  data: {
	                         authorities: [],
	                         pageTitle: 'Dịch vụ tòa nhà'
	                  },
	                  templateUrl: 'template/services.html'
                   })
                   .state('template.tarrif', {
	                  url: '/tarrif',
	                  data: {
	                         authorities: [],
	                         pageTitle: 'Loại phòng cung cấp'
	                     },
	                  templateUrl: 'template/tarrif.html'
                   })
                   .state('template.feedback', {
	                  url: '/feedback',
	                  data: {
	                         authorities: [],
	                         pageTitle: 'Ý kiến phản hồi'
	                     },
	                  templateUrl: 'template/feedback.html'
                   })
                   .state('template.reservation', {
	                  url: '/reservation',
	                  data: {
	                         authorities: [],
	                         pageTitle: 'Tìm kiếm phòng trống'
	                  },
	                  templateUrl: 'template/reservation/search.html',
	                  controller: 'searchCtrl',
	                  resolve: {
	                        deps: ['uiLoad',
	                            function( uiLoad){
	                               return uiLoad.load('js/app/template/search.js');
	                        }]
	                  }
                   })
                   .state('template.detail', {
	                  url: '/detail/{id}',
	                  data: {
	                         authorities: [],
	                         pageTitle: 'Thông tin chi tiết phòng'
	                  },
	                  templateUrl: 'template/reservation/single.html',
	                  controller: 'detailCtrl',
	                  resolve: {
	                        deps: ['uiLoad',
	                            function( uiLoad){
	                               return uiLoad.load('js/app/template/detail.js');
	                        }]
	                  }
                   })
                   .state('template.book', {
	                  url: '/book?idRoom&checkin&checkout?message',
	                  data: {
	                         authorities: [],
	                         pageTitle: 'Đăng ký đặt phòng tòa nhà'
	                  },
	                  templateUrl: 'template/reservation/book.html',
	                  resolve: {
	                         deps: ['uiLoad',
	                              function( uiLoad){
	                                   return uiLoad.load('js/app/template/bookingroom.js');
	                              }]
	                  }
                   })
                   .state('template.resultRegister', {
	                  url: '/resultRegister?id',
	                  data: {
	                         authorities: [],
	                         pageTitle: 'Đăng ký đặt phòng tòa nhà'
	                  },
	                  templateUrl: 'template/reservation/result.html',
	                  controller:'resultCtrl',
	                  resolve: {
	                         deps: ['uiLoad',
	                              function( uiLoad){
	                                   return uiLoad.load('js/app/template/resultCtrl.js');
	                              }]
	                  }
                   })
                   .state('template.apartment', {
	                  url: '/apartment',
	                  data: {
	                         authorities: [],
	                         pageTitle: 'Dịch vụ tòa nhà'
	                  },
	                  templateUrl: 'template/apartment/layout.html'
                   })
                   .state('template.apartment.home', {
	                  url: '/home',
	                  data: {
	                         authorities: [],
	                         pageTitle: 'Dịch vụ tòa nhà'
	                  },
	                  templateUrl: 'template/apartment/index.html'
                   })
                   .state('template.apartment.background', {
	                  url: '/background',
	                  data: {
	                         authorities: [],
	                         pageTitle: 'Dịch vụ tòa nhà'
	                  },
	                  templateUrl: 'template/apartment/background.html'
                   })
                   .state('template.apartment.amenity', {
	                  url: '/amenity',
	                  data: {
	                         authorities: [],
	                         pageTitle: 'Dịch vụ tòa nhà'
	                  },
	                  templateUrl: 'template/apartment/amenity.html'
                   })
                   
                   .state('template.office', {
	                  url: '/office',
	                  data: {
	                         authorities: [],
	                         pageTitle: 'Dịch vụ tòa nhà'
	                  },
	                  templateUrl: 'template/office/layout.html'
                   })
                   .state('template.office.home', {
	                  url: '/home',
	                  data: {
	                         authorities: [],
	                         pageTitle: 'Dịch vụ tòa nhà'
	                  },
	                  templateUrl: 'template/office/index.html'
                   })
                   .state('template.office.background', {
	                  url: '/background',
	                  data: {
	                         authorities: [],
	                         pageTitle: 'Dịch vụ tòa nhà'
	                  },
	                  templateUrl: 'template/office/background.html'
                   })
                   .state('template.office.amenity', {
	                  url: '/amenity',
	                  data: {
	                         authorities: [],
	                         pageTitle: 'Dịch vụ tòa nhà'
	                  },
	                  templateUrl: 'template/office/amenity.html'
                   })
                   
                   .state('template.event', {
	                  url: '/event',
	                  data: {
	                         authorities: [],
	                         pageTitle: 'Dịch vụ tòa nhà'
	                  },
	                  templateUrl: 'template/events/layout.html'
	                  
                   })
                   
                   .state('template.event.detail', {
	                  url: '/detail?id',
	                  data: {
	                         authorities: [],
	                         pageTitle: 'Sự kiện tòa nhà'
	                  },
	                  templateUrl: 'template/events/index.html',
	                  controller:"eventCtrl",
	                  resolve: {
	                         deps: ['uiLoad',
	                              function( uiLoad){
	                                   return uiLoad.load('js/app/template/event.js');
	                              }]
	                  }
                   })
                   .state('template.intro', {
	                  url: '/intro',
	                  data: {
	                         authorities: [],
	                         pageTitle: 'Dịch vụ tòa nhà'
	                  },
	                  templateUrl: 'template/intro/layout.html'
                   })
                   .state('template.intro.home', {
	                  url: '/home',
	                  data: {
	                         authorities: [],
	                         pageTitle: 'giới thiệu DMCTOWER'
	                  },
	                  templateUrl: 'template/intro/index.html'
                   })
                   .state('template.intro.apartment', {
	                  url: '/apartment',
	                  data: {
	                         authorities: [],
	                         pageTitle: 'giới thiệu DMCTOWER'
	                  },
	                  templateUrl: 'template/intro/apartment.html'
                   })
                   .state('template.intro.background1', {
	                  url: '/background1',
	                  data: {
	                         authorities: [],
	                         pageTitle: 'giới thiệu DMCTOWER'
	                  },
	                  templateUrl: 'template/intro/background1.html'
                   })
                   .state('template.intro.background26', {
	                  url: '/background26',
	                  data: {
	                         authorities: [],
	                         pageTitle: 'giới thiệu DMCTOWER'
	                  },
	                  templateUrl: 'template/intro/background26.html'
                   })
                   .state('template.intro.background17', {
	                  url: '/background17',
	                  data: {
	                         authorities: [],
	                         pageTitle: 'giới thiệu DMCTOWER'
	                  },
	                  templateUrl: 'template/intro/background17.html'
                   })
      }
    ]
  );