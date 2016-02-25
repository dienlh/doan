'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('images', {
                parent: 'entity',
                url: '/imagess',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Imagess'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/images/imagess.html',
                        controller: 'ImagesController'
                    }
                },
                resolve: {
                }
            })
            .state('images.detail', {
                parent: 'entity',
                url: '/images/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Images'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/images/images-detail.html',
                        controller: 'ImagesDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Images', function($stateParams, Images) {
                        return Images.get({id : $stateParams.id});
                    }]
                }
            })
            .state('images.new', {
                parent: 'images',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/images/images-dialog.html',
                        controller: 'ImagesDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    url: null,
                                    create_date: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('images', null, { reload: true });
                    }, function() {
                        $state.go('images');
                    })
                }]
            })
            .state('images.edit', {
                parent: 'images',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/images/images-dialog.html',
                        controller: 'ImagesDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Images', function(Images) {
                                return Images.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('images', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('images.delete', {
                parent: 'images',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/images/images-delete-dialog.html',
                        controller: 'ImagesDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Images', function(Images) {
                                return Images.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('images', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
