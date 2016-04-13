'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('school', {
                parent: 'app',
                url: '/schools',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Schools'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/entities/school/schools.html',
                        controller: 'SchoolController'
                    }
                },
                resolve: {
                }
            })
            .state('school.detail', {
                parent: 'app',
                url: '/school/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'School'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/entities/school/school-detail.html',
                        controller: 'SchoolDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'School', function($stateParams, School) {
                        return School.get({id : $stateParams.id});
                    }]
                }
            })
            .state('school.new', {
                parent: 'school',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/school/school-dialog.html',
                        controller: 'SchoolDialogController',
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
                        $state.go('school', null, { reload: true });
                    }, function() {
                        $state.go('school');
                    })
                }]
            })
            .state('school.edit', {
                parent: 'school',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/school/school-dialog.html',
                        controller: 'SchoolDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['School', function(School) {
                                return School.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('school', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('school.delete', {
                parent: 'school',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/school/school-delete-dialog.html',
                        controller: 'SchoolDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['School', function(School) {
                                return School.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('school', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
