'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('amenity', {
                parent: 'entity',
                url: '/amenitys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Amenitys'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/amenity/amenitys.html',
                        controller: 'AmenityController'
                    }
                },
                resolve: {
                }
            })
            .state('amenity.detail', {
                parent: 'entity',
                url: '/amenity/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Amenity'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/amenity/amenity-detail.html',
                        controller: 'AmenityDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Amenity', function($stateParams, Amenity) {
                        return Amenity.get({id : $stateParams.id});
                    }]
                }
            })
            .state('amenity.new', {
                parent: 'amenity',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/amenity/amenity-dialog.html',
                        controller: 'AmenityDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    create_date: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('amenity', null, { reload: true });
                    }, function() {
                        $state.go('amenity');
                    })
                }]
            })
            .state('amenity.edit', {
                parent: 'amenity',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/amenity/amenity-dialog.html',
                        controller: 'AmenityDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Amenity', function(Amenity) {
                                return Amenity.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('amenity', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('amenity.delete', {
                parent: 'amenity',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/amenity/amenity-delete-dialog.html',
                        controller: 'AmenityDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Amenity', function(Amenity) {
                                return Amenity.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('amenity', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
