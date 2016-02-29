'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('major', {
                parent: 'app',
                url: '/majors',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Majors'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/entities/major/majors.html',
                        controller: 'MajorController'
                    }
                },
                resolve: {
                }
            })
            .state('major.detail', {
                parent: 'app',
                url: '/major/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Major'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/entities/major/major-detail.html',
                        controller: 'MajorDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Major', function($stateParams, Major) {
                        return Major.get({id : $stateParams.id});
                    }]
                }
            })
            .state('major.new', {
                parent: 'major',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/major/major-dialog.html',
                        controller: 'MajorDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    decription: null,
                                    create_date: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('major', null, { reload: true });
                    }, function() {
                        $state.go('major');
                    })
                }]
            })
            .state('major.edit', {
                parent: 'major',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/major/major-dialog.html',
                        controller: 'MajorDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Major', function(Major) {
                                return Major.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('major', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('major.delete', {
                parent: 'major',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/major/major-delete-dialog.html',
                        controller: 'MajorDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Major', function(Major) {
                                return Major.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('major', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
