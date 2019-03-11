import { Router } from 'express'
import { middleware as query } from 'querymen'
import { middleware as body } from 'bodymen'
import { create, index, show, update, destroy } from './controller'
import { schema } from './model'
import { token, master } from '../../services/passport'
export Photo, { schema } from './model'


const router = new Router()
const { noticia, imgurLink, deleteHash } = schema.tree

const multer = require('multer')
const storage = multer.memoryStorage()
const upload = multer({storage: storage})

/**
 * @api {post} /photos Create photo
 * @apiName CreatePhoto
 * @apiGroup Photo
 * @apiParam noticia Photo's noticia.
 * @apiParam imgurLink Photo's imgurLink.
 * @apiParam deleteHash Photo's deleteHash.
 * @apiSuccess {Object} photo Photo's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Photo not found.
 */
router.post('/',
  token(),
  upload.single('photo'),
  // body({ noticia, imgurLink, deleteHash }),
  create)

/**
 * @api {get} /photos Retrieve photos
 * @apiName RetrievePhotos
 * @apiGroup Photo
 * @apiUse listParams
 * @apiSuccess {Number} count Total amount of photos.
 * @apiSuccess {Object[]} rows List of photos.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 */
router.get('/',
  query(),
  token(),
  index)

/**
 * @api {get} /photos/:id Retrieve photo
 * @apiName RetrievePhoto
 * @apiGroup Photo
 * @apiSuccess {Object} photo Photo's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Photo not found.
 */
router.get('/:id',
  token(),
  show)

/**
 * @api {put} /photos/:id Update photo
 * @apiName UpdatePhoto
 * @apiGroup Photo
 * @apiParam noticia Photo's noticia.
 * @apiParam imgurLink Photo's imgurLink.
 * @apiParam deleteHash Photo's deleteHash.
 * @apiSuccess {Object} photo Photo's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Photo not found.
 */
router.put('/:id',
  body({ noticia, imgurLink, deleteHash }),
  token(),
  update)

/**
 * @api {delete} /photos/:id Delete photo
 * @apiName DeletePhoto
 * @apiGroup Photo
 * @apiSuccess (Success 204) 204 No Content.
 * @apiError 404 Photo not found.
 */
router.delete('/:id',
  token(),
  destroy)

export default router