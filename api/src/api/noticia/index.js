import { Router } from 'express'
import { middleware as query } from 'querymen'
import { middleware as body } from 'bodymen'
import { create, index, show, update, destroy } from './controller'
import { schema } from './model'
import { token, master } from '../../services/passport'
export Noticia, { schema } from './model'

const router = new Router()
const { title, description, likes, comentarios, localizacion, photos, autor } = schema.tree

/**
 * @api {post} /noticias Create noticia
 * @apiName CreateNoticia
 * @apiGroup Noticia
 * @apiParam title Noticia's title.
 * @apiParam description Noticia's description.
 * @apiParam likes Noticia's likes.
 * @apiParam comentarios Noticia's comentarios.
 * @apiParam localizacion Noticia's localizacion.
 * @apiParam photos Noticia's photos.
 * @apiParam autor Noticia's autor.
 * @apiSuccess {Object} noticia Noticia's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Noticia not found.
 */
router.post('/',
  token({ required: true }),
  body({ title, description, likes, comentarios, localizacion, photos, autor }),
  create)

/**
 * @api {get} /noticias Retrieve noticias
 * @apiName RetrieveNoticias
 * @apiGroup Noticia
 * @apiUse listParams
 * @apiSuccess {Number} count Total amount of noticias.
 * @apiSuccess {Object[]} rows List of noticias.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 */
router.get('/',
  token({ required: true }),
  query(),
  index)

/**
 * @api {get} /noticias/:id Retrieve noticia
 * @apiName RetrieveNoticia
 * @apiGroup Noticia
 * @apiSuccess {Object} noticia Noticia's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Noticia not found.
 */
router.get('/:id',
  token({ required: true }),
  show)

/**
 * @api {put} /noticias/:id Update noticia
 * @apiName UpdateNoticia
 * @apiGroup Noticia
 * @apiParam title Noticia's title.
 * @apiParam description Noticia's description.
 * @apiParam likes Noticia's likes.
 * @apiParam comentarios Noticia's comentarios.
 * @apiParam localizacion Noticia's localizacion.
 * @apiParam photos Noticia's photos.
 * @apiParam autor Noticia's autor.
 * @apiSuccess {Object} noticia Noticia's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Noticia not found.
 */
router.put('/:id',
  token({ required: true }),
  body({ title, description, likes, comentarios, localizacion, photos, autor }),
  update)

/**
 * @api {delete} /noticias/:id Delete noticia
 * @apiName DeleteNoticia
 * @apiGroup Noticia
 * @apiSuccess (Success 204) 204 No Content.
 * @apiError 404 Noticia not found.
 */
router.delete('/:id',
  token({ required: true }),
  destroy)

// router.delete('/:id',
//   token(roles: ['admin']),
//   destroyAdmin)

export default router
