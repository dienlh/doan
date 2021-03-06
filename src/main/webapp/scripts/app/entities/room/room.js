'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('room', {
                parent: 'app',
                url: '/rooms',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Rooms'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/entities/room/rooms.html',
                        controller: 'RoomController'
                    }
                },
                resolve: {
                }
            })
            .state('room.find', {
                parent: 'app',
                url: '/rooms/find',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Rooms'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/entities/room/findrooms.html',
                        controller: 'FindRoomController'
                    }
                },
                resolve: {
                }
            })
            .state('room.detail', {
                parent: 'app',
                url: '/room/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Room'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/entities/room/room-detail.html',
                        controller: 'RoomDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Room', function($stateParams, Room) {
                        return Room.get({id : $stateParams.id});
                    }]
                }
            })
            .state('room.new', {
                parent: 'room',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/room/room-dialog.html',
                        controller: 'RoomDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    code: null,
                                    key_code: null,
                                    title: null,
                                    is_pet: false,
                                    is_bed_kid: false,
                                    number_of_livingroom: null,
                                    number_of_bedroom: null,
                                    number_of_toilet: null,
                                    number_of_kitchen: null,
                                    number_of_bathroom: null,
                                    floor: null,
                                    orientation: null,
                                    surface_size: null,
                                    max_adults: null,
                                    max_kids: null,
                                    hourly_price: null,
                                    daily_price: null,
                                    monthly_price: null,
                                    create_date: null,
                                    last_modified_date: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('room', null, { reload: true });
                    }, function() {
                        $state.go('room');
                    })
                }]
            })
            .state('room.edit', {
                parent: 'room',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/room/room-dialog.html',
                        controller: 'RoomDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Room', function(Room) {
                                return Room.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('room', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('room.delete', {
                parent: 'room',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/room/room-delete-dialog.html',
                        controller: 'RoomDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Room', function(Room) {
                                return Room.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('room', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('room.upload', {
                parent: 'room.detail',
                url: '/upload?message',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/room/uploadImage.html',
                        controller: 'RoomDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Room', function(Room) {
                                return Room.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('room', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('room.import', {
                parent: 'room',
                url: '/import',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/room/room-import.html',
//                        controller: 'RoomDialogController',
                        size: 'lg',
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
                    }).result.then(function(result) {
                        $state.go('room', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            ;
    });
