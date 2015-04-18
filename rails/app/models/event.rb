class Event < ActiveRecord::Base
	has_many :feeds
	validates :title, presence: true,
                	  length: { minimum: 5 }
end
